package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDTO;
import com.example.pokemon.entity.PokemonEntity;
import com.example.pokemon.mapper.PokemonMapper;
import com.example.pokemon.repository.PokemonRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PokemonService {
    private final PokemonRepository repository;

    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    public List<PokemonDTO> getAll() {
        return repository.findAll().stream()
                .map(PokemonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PokemonDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(PokemonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PokemonDTO save(PokemonEntity entity) {
        return PokemonMapper.toDTO(repository.save(entity));
    }

    public List<PokemonDTO> loadAndSavePokemon() {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://pokeapi.co/api/v2/pokemon?limit=100";

        String response = restTemplate.getForObject(baseUrl, String.class);
        JSONObject json = new JSONObject(response);
        JSONArray results = json.getJSONArray("results");

        List<PokemonDTO> saved = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            String url = results.getJSONObject(i).getString("url");
            String pokemonData = restTemplate.getForObject(url, String.class);
            JSONObject pokemonJson = new JSONObject(pokemonData);

            String name = pokemonJson.getString("name");
            int height = pokemonJson.getInt("height");
            String image = pokemonJson.getJSONObject("sprites").getString("front_default");

            JSONArray abilitiesArray = pokemonJson.getJSONArray("abilities");
            List<String> abilities = new ArrayList<>();
            for (int j = 0; j < abilitiesArray.length(); j++) {
                abilities.add(abilitiesArray.getJSONObject(j)
                        .getJSONObject("ability")
                        .getString("name"));
            }

            PokemonEntity entity = new PokemonEntity();
            entity.setName(name);
            entity.setType(abilities.isEmpty() ? "unknown" : String.join(",", abilities));
            repository.save(entity);

            PokemonDTO dto = new PokemonDTO();
            dto.setName(name);
            dto.setImage(image);
            dto.setHeight(height);
            dto.setAbilities(abilities);

            saved.add(dto);
        }

        return saved;
    }
}