package com.example.pokemon.dto;

import java.util.List;

public class PokemonDTO {
    private String name;
    private String type;
    private String image;
    private int height;
    private List<String> abilities;

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }
}
