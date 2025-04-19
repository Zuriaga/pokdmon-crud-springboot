package com.example.pokemon.repository;

import com.example.pokemon.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PokemonRepository extends JpaRepository<PokemonEntity, Long> {
    List<PokemonEntity> findByNameContainingIgnoreCase(String name);
}