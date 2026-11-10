package com.vibzz.music.controller;

import com.vibzz.music.model.Song;
import com.vibzz.music.repository.SongJdbcRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

/**
 * Task 4 Showcase: Simulated Microservices Mesh Controller
 * Exposes the Gateway, Catalog Service, and Interaction Service APIs
 * so the interactive Task 4 Explorer on the live web platform works seamlessly!
 */
@RestController
@CrossOrigin(origins = "*")
public class MicroservicesSimulationController {

    private final SongJdbcRepository repository;

    public MicroservicesSimulationController(SongJdbcRepository repository) {
        this.repository = repository;
    }

    // ==========================================
    // 1. API GATEWAY ROUTED ENDPOINTS (Port 8080)
    // ==========================================

    @GetMapping("/api/gateway/topology")
    public ResponseEntity<Map<String, Object>> getMeshTopology() {
        return ResponseEntity.ok(Map.of(
            "gateway", "Harris Jayaraj Vibzz Cloud API Gateway",
            "version", "3.2.5",
            "routingStrategy", "Path-Based Reverse Proxy (Spring Cloud Gateway / Zuul compatible)",
            "timestamp", Instant.now().toString(),
            "uptime", "99.98%",
            "microservices", List.of(
                Map.of(
                    "serviceId", "catalog-service",
                    "port", 8081,
                    "status", "HEALTHY",
                    "routes", List.of("/api/catalog/songs", "/api/catalog/songs/{id}"),
                    "responsibilities", "Track catalog, metadata, streaming audio CDN links"
                ),
                Map.of(
                    "serviceId", "interaction-service",
                    "port", 8082,
                    "status", "HEALTHY",
                    "routes", List.of("/api/interaction/favorites", "/api/interaction/plays/{id}", "/api/interaction/analytics"),
                    "responsibilities", "User engagement, real-time play counts, listening telemetry"
                )
            )
        ));
    }

    // ==========================================
    // 2. CATALOG MICROSERVICE (Port 8081 Routes)
    // ==========================================

    @GetMapping("/api/catalog/songs")
    public ResponseEntity<Map<String, Object>> getCatalogSongs() {
        List<Song> songs = repository.findAll();
        return ResponseEntity.ok(Map.of(
            "service", "catalog-service:8081",
            "count", songs.size(),
            "database", "Catalog-Isolated-DataStore",
            "tracks", songs
        ));
    }

    @GetMapping("/api/catalog/songs/{id}")
    public ResponseEntity<Map<String, Object>> getCatalogSongById(@PathVariable int id) {
        Song song = repository.findById(id);
        if (song != null) {
            return ResponseEntity.ok(Map.of(
                "service", "catalog-service:8081",
                "track", song,
                "cached", true,
                "cdnOrigin", "https://audio-ssl.itunes.apple.com"
            ));
        }
        return ResponseEntity.notFound().build();
    }

    // ==========================================
    // 3. INTERACTION MICROSERVICE (Port 8082 Routes)
    // ==========================================

    @GetMapping("/api/interaction/analytics")
    public ResponseEntity<Map<String, Object>> getInteractionAnalytics() {
        Map<String, Object> stats = repository.getPlaylistStatistics();
        List<Song> favorites = repository.findFavorites();
        
        return ResponseEntity.ok(Map.of(
            "service", "interaction-service:8082",
            "engine", "Event-Driven Telemetry Processor",
            "totalPlays", stats.getOrDefault("totalPlays", 0),
            "favoriteCount", favorites.size(),
            "mostPlayedSong", stats.getOrDefault("mostPlayedSong", "N/A"),
            "eventProcessingLatencyMs", 1.8,
            "kafkaTopic", "harris-music-user-events",
            "activeSessions", 42
        ));
    }

    @PostMapping("/api/interaction/plays/{id}")
    public ResponseEntity<Map<String, Object>> logPlayEvent(@PathVariable int id) {
        boolean success = repository.incrementPlayCount(id);
        Song s = repository.findById(id);
        return ResponseEntity.ok(Map.of(
            "service", "interaction-service:8082",
            "event", "SONG_PLAY_EVENT",
            "songId", id,
            "newPlayCount", s != null ? s.getPlayCount() : 0,
            "status", success ? "PROCESSED" : "ERROR",
            "timestamp", Instant.now().toString()
        ));
    }

    @PostMapping("/api/interaction/favorites/{id}")
    public ResponseEntity<Map<String, Object>> toggleFavoriteEvent(@PathVariable int id) {
        boolean fav = repository.toggleFavorite(id);
        return ResponseEntity.ok(Map.of(
            "service", "interaction-service:8082",
            "event", "USER_FAVORITE_TOGGLED",
            "songId", id,
            "isFavorite", fav,
            "timestamp", Instant.now().toString()
        ));
    }

    // ==========================================
    // 4. ACTUATOR / RESILIENCE HEALTH CHECK
    // ==========================================

    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, Object>> getSystemHealth() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", Instant.now().toString(),
            "components", Map.of(
                "apiGateway", Map.of("status", "UP", "port", 8080, "load", "0.12"),
                "catalogService", Map.of("status", "UP", "port", 8081, "heapMb", 48),
                "interactionService", Map.of("status", "UP", "port", 8082, "subscribers", 12),
                "neonDatabase", Map.of("status", "UP", "pooler", "PgBouncer", "ssl", true)
            )
        ));
    }
}
