package com.vibzz.music.repository;

import com.vibzz.music.model.Song;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ====================================================================
 * SongJdbcRepository - College Project: CLA-II Task 3
 * Demonstrates low-level Java Database Connectivity (JDBC) API:
 * - DataSource connection pooling
 * - PreparedStatement for parameterized SQL (protects against SQL Injection)
 * - ResultSet row iteration and Object-Relational mapping
 * - Transactional updates and audit logging
 * ====================================================================
 */
@Repository
public class SongJdbcRepository {

    private final DataSource dataSource;

    public SongJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Map a ResultSet row to a Song model object
     */
    private Song mapRowToSong(ResultSet rs) throws SQLException {
        Song song = new Song();
        song.setId(rs.getInt("id"));
        song.setTitle(rs.getString("title"));
        song.setMovie(rs.getString("movie"));
        song.setReleaseYear(rs.getString("release_year"));
        song.setDuration(rs.getString("duration"));
        song.setQuery(rs.getString("query"));
        song.setTags(rs.getString("tags"));
        song.setPlayCount(rs.getInt("play_count"));
        song.setFavorite(rs.getBoolean("is_favorite"));
        if (rs.getTimestamp("created_at") != null) {
            song.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return song;
    }

    /**
     * SELECT * FROM songs ORDER BY id ASC (Raw JDBC)
     */
    public List<Song> findAll() {
        String sql = "SELECT * FROM songs ORDER BY id ASC";
        List<Song> songs = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                songs.add(mapRowToSong(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error executing findAll via JDBC: " + e.getMessage(), e);
        }

        return songs;
    }

    /**
     * SELECT * FROM songs WHERE id = ? (Raw JDBC Parameterized Query)
     */
    public Song findById(int id) {
        String sql = "SELECT * FROM songs WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToSong(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error finding song by id: " + id, e);
        }

        return null;
    }

    /**
     * UPDATE songs SET play_count = play_count + 1 WHERE id = ?
     * Also records an audit entry into play_logs
     */
    public boolean incrementPlayCount(int songId) {
        String updateSql = "UPDATE songs SET play_count = play_count + 1 WHERE id = ?";
        String logSql = "INSERT INTO play_logs (song_id) VALUES (?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false); // Begin JDBC Transaction

            try (PreparedStatement updatePs = conn.prepareStatement(updateSql);
                 PreparedStatement logPs = conn.prepareStatement(logSql)) {

                updatePs.setInt(1, songId);
                int rows = updatePs.executeUpdate();

                logPs.setInt(1, songId);
                logPs.executeUpdate();

                conn.commit(); // Commit Transaction
                return rows > 0;
            } catch (SQLException ex) {
                conn.rollback(); // Rollback on failure
                throw ex;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Transaction error incrementing play count for song: " + songId, e);
        }
    }

    /**
     * Toggle favorite status in songs and favorites table
     */
    public boolean toggleFavorite(int songId) {
        Song song = findById(songId);
        if (song == null) return false;

        boolean newFavStatus = !song.isFavorite();
        String updateSongSql = "UPDATE songs SET is_favorite = ? WHERE id = ?";
        String insertFavSql = "INSERT INTO favorites (song_id) VALUES (?)";
        String deleteFavSql = "DELETE FROM favorites WHERE song_id = ?";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psUpdate = conn.prepareStatement(updateSongSql)) {
                psUpdate.setBoolean(1, newFavStatus);
                psUpdate.setInt(2, songId);
                psUpdate.executeUpdate();

                if (newFavStatus) {
                    try (PreparedStatement psFav = conn.prepareStatement(insertFavSql)) {
                        psFav.setInt(1, songId);
                        psFav.executeUpdate();
                    }
                } else {
                    try (PreparedStatement psDel = conn.prepareStatement(deleteFavSql)) {
                        psDel.setInt(1, songId);
                        psDel.executeUpdate();
                    }
                }

                conn.commit();
                return newFavStatus;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error toggling favorite status via JDBC", e);
        }
    }

    /**
     * SELECT * FROM songs WHERE is_favorite = TRUE
     */
    public List<Song> findFavorites() {
        String sql = "SELECT * FROM songs WHERE is_favorite = TRUE ORDER BY title ASC";
        List<Song> favs = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                favs.add(mapRowToSong(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching favorites via JDBC", e);
        }

        return favs;
    }

    /**
     * Aggregate SQL Query (COUNT, SUM, MAX)
     */
    public Map<String, Object> getPlaylistStatistics() {
        String statsSql = "SELECT COUNT(*) as total_tracks, SUM(play_count) as total_plays FROM songs";
        String topSongSql = "SELECT title, movie, play_count FROM songs ORDER BY play_count DESC LIMIT 1";

        Map<String, Object> stats = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statsSql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.put("totalTracks", rs.getInt("total_tracks"));
                    stats.put("totalPlays", rs.getInt("total_plays"));
                }
            }

            try (PreparedStatement psTop = conn.prepareStatement(topSongSql);
                 ResultSet rsTop = psTop.executeQuery()) {
                if (rsTop.next()) {
                    stats.put("mostPlayedSong", rsTop.getString("title"));
                    stats.put("mostPlayedMovie", rsTop.getString("movie"));
                    stats.put("highestPlayCount", rsTop.getInt("play_count"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating SQL statistics via JDBC", e);
        }

        return stats;
    }
}
