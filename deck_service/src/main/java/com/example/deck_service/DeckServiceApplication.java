package com.example.deck_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DeckServiceApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");

        SpringApplication.run(DeckServiceApplication.class, args);
    }

}
