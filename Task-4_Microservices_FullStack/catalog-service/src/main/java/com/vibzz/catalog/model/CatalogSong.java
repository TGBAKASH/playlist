package com.vibzz.catalog.model;

public record CatalogSong(
    int id,
    String title,
    String movie,
    String year,
    String duration,
    String query,
    String tags
) {}
