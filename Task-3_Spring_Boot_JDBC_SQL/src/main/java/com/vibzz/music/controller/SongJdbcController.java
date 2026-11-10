package com.vibzz.music.controller;

import com.vibzz.music.model.Song;
import com.vibzz.music.repository.SongJdbcRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller exposing JDBC-backed endpoints for Harris Jayaraj Playlist
 */
@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*") // Allows calls from Task 1 (HTML/CSS) and Task 2 (React)
public class SongJdbcController {

    private final SongJdbcRepository repository;

    public SongJdbcController(SongJdbcRepository repository) {
        this.repository = repository;
    }

    /**
     * GET /api/songs - Fetch all 16 tracks from database
     */
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * GET /api/songs/{id} - Fetch song by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable int id) {
        Song song = repository.findById(id);
        if (song != null) {
            return ResponseEntity.ok(song);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * POST /api/songs/{id}/play - Increment play count in SQL table
     */
    @PostMapping("/{id}/play")
    public ResponseEntity<Map<String, Object>> logSongPlay(@PathVariable int id) {
        boolean success = repository.incrementPlayCount(id);
        if (success) {
            Song updated = repository.findById(id);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "songId", id,
                    "newPlayCount", updated.getPlayCount()
            ));
        }
        return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Song ID not found"));
    }

    /**
     * POST /api/songs/{id}/favorite - Toggle favorite in database
     */
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@PathVariable int id) {
        boolean isFav = repository.toggleFavorite(id);
        return ResponseEntity.ok(Map.of(
                "songId", id,
                "isFavorite", isFav
        ));
    }

    /**
     * GET /api/songs/favorites - Get all favorited tracks
     */
    @GetMapping("/favorites")
    public ResponseEntity<List<Song>> getFavorites() {
        return ResponseEntity.ok(repository.findFavorites());
    }

    /**
     * GET /api/songs/stats - SQL Aggregate stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        return ResponseEntity.ok(repository.getPlaylistStatistics());
    }
}
