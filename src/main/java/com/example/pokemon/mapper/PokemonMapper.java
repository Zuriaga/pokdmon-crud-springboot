package com.example.pokemon.mapper;

import com.example.pokemon.dto.PokemonDTO;
import com.example.pokemon.entity.PokemonEntity;

import java.util.Arrays;

public class PokemonMapper {

    public static PokemonDTO toDTO(PokemonEntity entity) {
        PokemonDTO dto = new PokemonDTO();
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setImage(entity.getImage());
        dto.setHeight(entity.getHeight());

        if (entity.getAbilities() != null) {
            dto.setAbilities(Arrays.asList(entity.getAbilities().split(",")));
        }
        return dto;
    }

    public static PokemonEntity toEntity(PokemonDTO dto) {
        PokemonEntity entity = new PokemonEntity();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setImage(dto.getImage());
        entity.setHeight(dto.getHeight());

        if (dto.getAbilities() != null) {
            entity.setAbilities(String.join(",", dto.getAbilities()));
        }
        return entity;
    }
}
