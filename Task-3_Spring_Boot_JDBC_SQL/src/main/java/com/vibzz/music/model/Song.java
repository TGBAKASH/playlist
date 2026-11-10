package com.vibzz.music.model;

import java.time.LocalDateTime;

public class Song {
    private int id;
    private String title;
    private String movie;
    private String releaseYear;
    private String duration;
    private String query;
    private String tags;
    private int playCount;
    private boolean isFavorite;
    private LocalDateTime createdAt;

    public Song() {}

    public Song(int id, String title, String movie, String releaseYear, String duration, String query, String tags, int playCount, boolean isFavorite, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.movie = movie;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.query = query;
        this.tags = tags;
        this.playCount = playCount;
        this.isFavorite = isFavorite;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMovie() { return movie; }
    public void setMovie(String movie) { this.movie = movie; }

    public String getReleaseYear() { return releaseYear; }
    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public int getPlayCount() { return playCount; }
    public void setPlayCount(int playCount) { this.playCount = playCount; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
