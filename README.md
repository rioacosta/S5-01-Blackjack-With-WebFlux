# 🃏 S5-01 Blackjack With WebFlux

Bienvenido a **Blackjack API**, una aplicación web reactiva construida con **Spring Boot + WebFlux** que simula partidas de Blackjack entre un jugador y el crupier. Esta API está diseñada para ser rápida, escalable y fácil de consumir desde cualquier frontend o cliente HTTP.

---

## 🚀 Tecnologías utilizadas

- ⚙️ Java 17
- 🌐 Spring Boot 3
- 🔁 Spring WebFlux (programación reactiva)
- 🐳 Docker & Docker Compose
- 🍃 MongoDB (base de datos NoSQL)
- 📄 Swagger/OpenAPI para documentación interactiva

---

## 🧠 Funcionalidades principales

- Crear nuevas partidas de Blackjack
- Pedir carta o plantarse
- Lógica completa del juego (Blackjack natural, reglas del crupier, puntuación con Ases)
- Persistencia de partidas en MongoDB
- Ranking de jugadores por número de victorias
- API documentada con Swagger

---

## 📦 Cómo ejecutar el proyecto

### Opción 1: Docker (recomendado)

```bash
docker run -p 8080:8080 rioacosta/s5-01-blackjack-with-webflux-blackjack-api:1.0
Opción 2: Docker Compose (con MongoDB)
bash
docker-compose up --build
Asegúrate de tener Docker y Docker Compose instalados.

🌐 Endpoints útiles
Recurso	URL
Swagger UI	http://localhost:8080/webjars/swagger-ui/index.html
API Docs	http://localhost:8080/v3/api-docs
Health Check	http://localhost:8080/actuator/health
🧪 Ejemplo de flujo de juego
Crear partida: POST /games

Pedir carta: PUT /games/{id}?askForCard=true

Plantarse: PUT /games/{id}?askForCard=false

Ver estado: GET /games/{id}

Ver ranking: GET /games/ranking

🛠️ En desarrollo
Autenticación con JWT

Frontend en React o Angular

Modo multijugador

🤝 Contribuciones
¡Las contribuciones son bienvenidas! Puedes abrir issues, enviar pull requests o sugerencias.
