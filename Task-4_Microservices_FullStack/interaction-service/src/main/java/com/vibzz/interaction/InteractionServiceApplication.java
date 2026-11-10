package com.vibzz.interaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InteractionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InteractionServiceApplication.class, args);
        System.out.println("📊 Interaction & Analytics Microservice running on port 8082");
    }
}
