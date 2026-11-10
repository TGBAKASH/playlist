package com.vibzz.interaction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/interaction")
@CrossOrigin(origins = "*")
public class InteractionController {

    private final Set<Integer> favorites = Collections.synchronizedSet(new HashSet<>(List.of(0, 3, 8, 10, 13, 14)));
    private final Map<Integer, Integer> playCounts = new ConcurrentHashMap<>();

    public InteractionController() {
        playCounts.put(0, 1420);
        playCounts.put(3, 1650);
        playCounts.put(8, 1890);
        playCounts.put(14, 1980);
    }

    @GetMapping("/favorites")
    public ResponseEntity<Set<Integer>> getFavorites() {
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/favorites/{songId}")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@PathVariable int songId) {
        boolean isFav;
        if (favorites.contains(songId)) {
            favorites.remove(songId);
            isFav = false;
        } else {
            favorites.add(songId);
            isFav = true;
        }
        return ResponseEntity.ok(Map.of("songId", songId, "isFavorite", isFav));
    }

    @PostMapping("/plays/{songId}")
    public ResponseEntity<Map<String, Object>> logPlay(@PathVariable int songId) {
        int newCount = playCounts.merge(songId, 1, Integer::sum);
        return ResponseEntity.ok(Map.of("songId", songId, "playCount", newCount));
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        int totalPlays = playCounts.values().stream().mapToInt(Integer::intValue).sum();
        return ResponseEntity.ok(Map.of(
            "totalPlaysRecorded", totalPlays,
            "totalFavoritesCount", favorites.size(),
            "activeUsersMock", 42
        ));
    }
}
