package com.vibzz.music.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

/**
 * Intelligent Multi-Environment DataSource Configuration
 * Seamlessly connects to Neon Cloud PostgreSQL on Render,
 * or falls back to local H2 in-memory database during local testing.
 *
 * Automatically parses single-string Cloud URLs:
 * postgresql://user:pass@host/db?sslmode=require
 */
@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.url:}")
    private String springUrl;

    @Value("${spring.datasource.username:}")
    private String springUser;

    @Value("${spring.datasource.password:}")
    private String springPassword;

    private String getSetting(String key) {
        String val = System.getenv(key);
        if (val == null || val.trim().isEmpty()) {
            val = System.getProperty(key);
        }
        return (val != null && !val.trim().isEmpty()) ? val.trim() : null;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        // Priority 1: Check standard cloud DATABASE_URL (Render / Neon)
        String rawUrl = getSetting("DATABASE_URL");
        if (rawUrl == null) {
            rawUrl = getSetting("SPRING_DATASOURCE_URL");
        }
        if (rawUrl == null && springUrl != null && !springUrl.contains("jdbc:h2:")) {
            rawUrl = springUrl.trim();
        }

        HikariConfig hikari = new HikariConfig();

        // Check if rawUrl is a Neon / Postgres Cloud URI (e.g., postgresql://user:pass@host/db)
        if (rawUrl != null && (rawUrl.startsWith("postgresql://") || rawUrl.startsWith("postgres://"))) {
            log.info("🐘 Detected Cloud PostgreSQL connection string (Neon / Render)");
            try {
                // Convert scheme temporarily to parse URI reliably
                String parseable = rawUrl.replaceFirst("^[a-zA-Z0-9]+://", "http://");
                URI uri = new URI(parseable);

                String host = uri.getHost();
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String database = uri.getPath(); // e.g. /neondb

                String extractedUser = null;
                String extractedPass = null;

                if (uri.getUserInfo() != null) {
                    String[] userParts = uri.getUserInfo().split(":", 2);
                    extractedUser = userParts[0];
                    if (userParts.length > 1) {
                        extractedPass = userParts[1];
                    }
                }

                // Prefer explicit env/property username/password if provided, otherwise use parsed credentials
                String envUser = getSetting("SPRING_DATASOURCE_USERNAME");
                String envPass = getSetting("SPRING_DATASOURCE_PASSWORD");

                String finalUser = (envUser != null) ? envUser :
                        (extractedUser != null ? extractedUser : springUser);
                String finalPass = (envPass != null) ? envPass :
                        (extractedPass != null ? extractedPass : springPassword);

                // Build clean JDBC URL
                String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + database + "?sslmode=require";

                log.info("🔗 Target Host: {}:{}", host, port);
                log.info("👤 Target User: {}", finalUser);
                log.info("🗄️ Target Database: {}", database);

                hikari.setJdbcUrl(jdbcUrl);
                hikari.setUsername(finalUser);
                hikari.setPassword(finalPass);
                hikari.setDriverClassName("org.postgresql.Driver");

            } catch (Exception ex) {
                log.error("Failed to parse Cloud database URI, falling back: {}", ex.getMessage());
                hikari.setJdbcUrl(rawUrl);
                hikari.setUsername(springUser);
                hikari.setPassword(springPassword);
            }
        } else if (rawUrl != null && rawUrl.startsWith("jdbc:postgresql://")) {
            log.info("🐘 Using standard JDBC PostgreSQL URL: {}", rawUrl);
            hikari.setJdbcUrl(rawUrl);
            hikari.setUsername(springUser);
            hikari.setPassword(springPassword);
            hikari.setDriverClassName("org.postgresql.Driver");
        } else {
            // Local fallback: In-memory H2 database
            log.info("💾 No Cloud Database configured. Using local H2 In-Memory SQL Database.");
            hikari.setJdbcUrl("jdbc:h2:mem:musicdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL");
            hikari.setUsername("sa");
            hikari.setPassword("");
            hikari.setDriverClassName("org.h2.Driver");
        }

        // HikariCP connection pool performance optimizations
        hikari.setMaximumPoolSize(10);
        hikari.setMinimumIdle(2);
        hikari.setIdleTimeout(30000);
        hikari.setConnectionTimeout(20000);
        hikari.setPoolName("HarrisJayaraj-HikariCP-Pool");

        return new HikariDataSource(hikari);
    }
}
