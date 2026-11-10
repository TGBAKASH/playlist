package com.vibzz.catalog.controller;

import com.vibzz.catalog.model.CatalogSong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/songs")
@CrossOrigin(origins = "*")
public class CatalogController {

    private final List<CatalogSong> catalog = List.of(
        new CatalogSong(0, "MUN ANDHI", "7 Aum Arivu", "2011", "6:14", "Mun Andhi Harris Jayaraj", "surya,romantic"),
        new CatalogSong(1, "MAZHAI VARA POGUDHAE", "Yennai Arindhaal", "2015", "5:42", "Mazhai Vara Pogudhae", "romantic"),
        new CatalogSong(2, "PUYALAE PUYALAE", "Kovil", "2004", "4:58", "Puyale Puyale", "classic"),
        new CatalogSong(3, "ONDRA RENDA", "Kaakha Kaakha", "2003", "5:06", "Ondra Renda", "surya,classic,romantic"),
        new CatalogSong(4, "RAGASIYA KANAVUGAL", "Bheema", "2008", "5:59", "Ragasiya Kanavugal", "romantic"),
        new CatalogSong(5, "THEE ILLAI", "Engeyum Kadhal", "2011", "5:27", "Thee Illai", "romantic"),
        new CatalogSong(6, "LESA LESA", "Lesa Lesa", "2003", "4:41", "Lesa Lesa Harris Jayaraj", "classic,romantic"),
        new CatalogSong(7, "EN FRIENDA POLA", "Nanban", "2012", "3:57", "En Frienda Pola", "classic"),
        new CatalogSong(8, "SUTTUM VIZHI", "Ghajini", "2005", "5:18", "Suttum Vizhi", "surya,classic,romantic"),
        new CatalogSong(9, "ENNAMO YEDHO", "Ko", "2011", "5:34", "Ennamo Yeadho", "romantic"),
        new CatalogSong(10, "UNNAALE UNNAALE", "Unnale Unnale", "2007", "4:43", "Unnale Unnale", "romantic"),
        new CatalogSong(11, "EN ANBE", "Sathyam", "2008", "6:07", "En Anbe Sathyam", "romantic"),
        new CatalogSong(12, "AATHANGARA ORATHIL", "Yaan", "2014", "4:24", "Aathangara Orathil", "romantic"),
        new CatalogSong(13, "PAARTHA MUDHAL", "Vettaiyaadu Vilaiyaadu", "2006", "6:08", "Paartha Mudhal", "romantic"),
        new CatalogSong(14, "AVA ENNA", "Vaaranam Aayiram", "2008", "5:18", "Ava Enna Vaaranam Aayiram", "surya,romantic"),
        new CatalogSong(15, "POOPOL POOPOL", "Minnalae", "2001", "1:57", "Poopol Poopol", "classic,romantic")
    );

    @GetMapping
    public ResponseEntity<List<CatalogSong>> getAllCatalogSongs() {
        return ResponseEntity.ok(catalog);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogSong> getSong(@PathVariable int id) {
        return catalog.stream()
                .filter(s -> s.id() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
