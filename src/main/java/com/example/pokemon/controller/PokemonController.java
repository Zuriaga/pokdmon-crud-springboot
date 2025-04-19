package com.example.pokemon.controller;

import com.example.pokemon.dto.PokemonDTO;
import com.example.pokemon.entity.PokemonEntity;
import com.example.pokemon.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonService service;

    public PokemonController(PokemonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PokemonDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PokemonDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }

    @PostMapping
    public ResponseEntity<PokemonDTO> save(@RequestBody PokemonEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @GetMapping("/load")
    public ResponseEntity<String> loadAndSave() {
        service.loadAndSavePokemons();
        return ResponseEntity.ok("✅ Pokémons cargados exitosamente");
    }
}
