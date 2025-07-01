# 🃏 S5-01 Blackjack With WebFlux

Bienvenido a **Blackjack API**, una aplicación web reactiva construida con **Spring Boot + WebFlux** que simula partidas de Blackjack entre un jugador y el crupier. Esta API está diseñada para ser rápida, escalable y fácil de consumir desde cualquier frontend o cliente HTTP.

---

## 🚀 Tecnologías utilizadas

- ⚙️ Java 21
- 🌐 Spring Boot 
- 🔁 Spring WebFlux (programación reactiva)
- 🐳 Docker & Docker Compose
- 🍃 MongoDB (base de datos NoSQL)
- 📄 Swagger/OpenAPI para documentación interactiva
- 📄 Postman para pruebas

---

## 🧠 Funcionalidades principales

- Crear nuevas partidas de Blackjack
- Pedir carta o plantarse
- Lógica completa del juego (Blackjack natural, reglas del crupier, puntuación con Ases)
- Persistencia de partidas en MongoDB
- Ranking de jugadores por número de victorias
- API documentada con Swagger

---

## 📦 Cómo ejecutar el proyecto con Docker

Asegúrate de tener Docker instalado y funcionando correctamente.

Desde la raiz del proyecto ejecuta:
```bash
docker-compose up --build
```
Esto iniciará:

MySQL (en el puerto 3307)

MongoDB (en el puerto 27017)

Blackjack API (en el puerto 8080)

---

##📚Endpoints:


<details>
  <summary>Endpoints útiles (click para expandir)</summary>

Swagger UI	http://localhost:8080/webjars/swagger-ui/index.html

API Docs	http://localhost:8080/v3/api-docs

Health Check	http://localhost:8080/actuator/health
</details>

<details>
  <summary>Todos los Endpoints para la API (Click para expandir)</summary>

<br>

### 🃏 **BlackjackController**

| Method | Endpoint                       | Descripción                                      |
|--------|--------------------------------|--------------------------------------------------|
| POST   | `/game/{gameId}/play`          | Jugar una partida ya creada                     |
| POST   | `/game/new`                    | Crear una nueva partida de Blackjack            |
| GET    | `/game`                        | Obtener todas las partidas jugadas              |
| GET    | `/game/{id}`                   | Ver detalles de una partida específica           |
| DELETE | `/game/{id}/delete`            | Eliminar una partida por ID                     |

---

### 👤 **PlayerController**

| Method | Endpoint                          | Descripción                          |
|--------|-----------------------------------|--------------------------------------|
| PUT    | `/players/{playerId}`             | Actualizar un jugador                |
| POST   | `/players`                        | Crear un nuevo jugador               |
| GET    | `/players/{id}`                   | Obtener un jugador por ID            |
| GET    | `/players/getAllPlayers`          | Listar todos los jugadores           |
| DELETE | `/players/{id}/delete`            | Eliminar un jugador                  |

---

### 🏆 **RankingController**

| Method | Endpoint               | Descripción                   |
|--------|------------------------|-------------------------------|
| GET    | `/ranking`     | Obtener el ranking de jugadores |

</details>

---


### 🧪 Ejemplo de flujo de juego
Crear partida:
POST /games

Pedir carta:
PUT /games/{id}?askForCard=true

Plantarse:
PUT /games/{id}?askForCard=false

Ver estado:
GET /games/{id}

Ver ranking:
GET /games/ranking

---

### 🤝 Contribuciones
¡Las contribuciones son bienvenidas!
Puedes abrir issues, enviar pull requests o sugerencias. 🙌
