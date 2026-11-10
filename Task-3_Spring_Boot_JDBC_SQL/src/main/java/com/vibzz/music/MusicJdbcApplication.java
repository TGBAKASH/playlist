package com.vibzz.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicJdbcApplication.class, args);
        System.out.println("=================================================");
        System.out.println("🎵 Harris Jayaraj Music JDBC Service Started!");
        System.out.println("📍 API Base URL: http://localhost:8080/api/songs");
        System.out.println("📊 H2 Console:   http://localhost:8080/h2-console");
        System.out.println("=================================================");
    }
}
