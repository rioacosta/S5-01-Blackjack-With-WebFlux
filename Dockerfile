

# -------- FASE 1: Build (compilar el proyecto) --------
FROM eclipse-temurin:21-jdk-alpine AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga dependencias primero
COPY pom.xml ./
RUN apk add --no-cache maven
RUN mvn dependency:go-offline

# Copia el código fuente del proyecto
COPY src ./src

# Compila y empaqueta la aplicación (crea el .jar)
RUN mvn clean package -DskipTests

# -------- FASE 2: Runtime (ejecutar el proyecto) --------
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo

WORKDIR /app

# Copia solo el .jar construido desde la fase anterior
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

