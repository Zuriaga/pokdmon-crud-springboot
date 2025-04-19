package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDTO;
import com.example.pokemon.entity.PokemonEntity;
import com.example.pokemon.mapper.PokemonMapper;
import com.example.pokemon.repository.PokemonRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public void loadAndSavePokemons() {
        try {
            String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=100";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(jsonBuilder.toString());
            JSONArray results = jsonResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject pokemonSummary = results.getJSONObject(i);
                String name = pokemonSummary.getString("name");
                String detailUrl = pokemonSummary.getString("url");


                HttpURLConnection detailConnection = (HttpURLConnection) new URL(detailUrl).openConnection();
                detailConnection.setRequestMethod("GET");

                BufferedReader detailReader = new BufferedReader(new InputStreamReader(detailConnection.getInputStream()));
                StringBuilder detailBuilder = new StringBuilder();
                String detailLine;
                while ((detailLine = detailReader.readLine()) != null) {
                    detailBuilder.append(detailLine);
                }
                detailReader.close();

                JSONObject pokemonDetails = new JSONObject(detailBuilder.toString());

                String image = pokemonDetails.getJSONObject("sprites").getString("front_default");
                int height = pokemonDetails.getInt("height");


                JSONArray typesArray = pokemonDetails.getJSONArray("types");
                List<String> typeList = new ArrayList<>();
                for (int t = 0; t < typesArray.length(); t++) {
                    typeList.add(typesArray.getJSONObject(t).getJSONObject("type").getString("name"));
                }


                JSONArray abilitiesArray = pokemonDetails.getJSONArray("abilities");
                List<String> abilityList = new ArrayList<>();
                for (int a = 0; a < abilitiesArray.length(); a++) {
                    abilityList.add(abilitiesArray.getJSONObject(a).getJSONObject("ability").getString("name"));
                }

                PokemonEntity entity = new PokemonEntity();
                entity.setName(name);
                entity.setType(String.join(",", typeList));
                entity.setImage(image);
                entity.setHeight(height);
                entity.setAbilities(String.join(",", abilityList));

                repository.save(entity);
            }

            System.out.println("100 Pokémons loaded and saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
