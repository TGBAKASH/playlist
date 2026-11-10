package com.vibzz.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class ApiGatewayApplication {

    @Value("${catalog.service.url:http://localhost:8081}")
    private String catalogUrl;

    @Value("${interaction.service.url:http://localhost:8082}")
    private String interactionUrl;

    private final RestTemplate restTemplate;

    public ApiGatewayApplication(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("🌐 Unified API Gateway running on port 8080");
        System.out.println("👉 Routes: /api/catalog/** -> Port 8081 | /api/interaction/** -> Port 8082");
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> gatewayStatus() {
        return ResponseEntity.ok(Map.of(
            "gateway", "Harris Jayaraj Vibzz API Gateway",
            "status", "ACTIVE",
            "microservices", Map.of(
                "catalogService", catalogUrl,
                "interactionService", interactionUrl
            )
        ));
    }

    // Route Catalog requests
    @GetMapping("/api/catalog/songs")
    public ResponseEntity<?> forwardCatalogSongs() {
        return restTemplate.getForEntity(catalogUrl + "/api/catalog/songs", Object.class);
    }

    @GetMapping("/api/catalog/songs/{id}")
    public ResponseEntity<?> forwardCatalogSongById(@PathVariable int id) {
        return restTemplate.getForEntity(catalogUrl + "/api/catalog/songs/" + id, Object.class);
    }

    // Route Interaction requests
    @GetMapping("/api/interaction/favorites")
    public ResponseEntity<?> forwardFavorites() {
        return restTemplate.getForEntity(interactionUrl + "/api/interaction/favorites", Object.class);
    }

    @PostMapping("/api/interaction/favorites/{id}")
    public ResponseEntity<?> forwardToggleFavorite(@PathVariable int id) {
        return restTemplate.postForEntity(interactionUrl + "/api/interaction/favorites/" + id, null, Object.class);
    }

    @PostMapping("/api/interaction/plays/{id}")
    public ResponseEntity<?> forwardLogPlay(@PathVariable int id) {
        return restTemplate.postForEntity(interactionUrl + "/api/interaction/plays/" + id, null, Object.class);
    }

    @GetMapping("/api/interaction/analytics")
    public ResponseEntity<?> forwardAnalytics() {
        return restTemplate.getForEntity(interactionUrl + "/api/interaction/analytics", Object.class);
    }
}
