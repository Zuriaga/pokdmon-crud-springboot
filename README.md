# Pokémon CRUD API con Spring Boot

Este proyecto fue desarrollado como parte de una prueba técnica para una posición de desarrollador Java Jr. Consiste en una API REST que permite consumir información de la PokéAPI, almacenarla en una base de datos MySQL local y exponerla mediante endpoints CRUD.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.1
- Spring Data JPA
- MySQL
- Maven
- IntelliJ IDEA
- Postman
- PokéAPI (https://pokeapi.co)

## Funcionalidades

- Obtener una lista de Pokémon desde la PokéAPI y almacenarlos en la base de datos local.
- Consultar todos los Pokémon almacenados.
- Buscar Pokémon por nombre (búsqueda parcial o exacta).
- Agregar nuevos Pokémon manualmente a través de un endpoint POST.

## Endpoints disponibles

- GET /pokemon/load  
  Carga los primeros 100 Pokémon desde la PokéAPI y los guarda en la base de datos.

- GET /pokemon  
  Retorna todos los Pokémon almacenados en la base de datos.

- GET /pokemon/search?name={nombre}  
  Busca Pokémon cuyo nombre contenga la cadena proporcionada.

- POST /pokemon  
  Agrega un nuevo Pokémon a la base de datos. Requiere un cuerpo JSON con los campos: name, type, image, height, abilities.

## Configuración y ejecución

1. Asegúrate de tener MySQL instalado y en funcionamiento.
2. Crea una base de datos llamada pokemon_db. Puedes usar el script proporcionado en database-init.sql.
3. Clona este repositorio:
   ```bash
   git clone https://github.com/Zuriaga/pokdmon-crud-springboot.git
   cd pokdmon-crud-springboot
