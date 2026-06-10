# Etapa 1: Compilación del proyecto
FROM gradle:8.14-jdk21-alpine AS build
WORKDIR /app

# 1. Copiar archivos base de Gradle para almacenar dependencias en caché
COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .

# 2. Copiar archivos de configuración de cada módulo hexagonal
COPY application/build.gradle.kts application/
COPY domain/build.gradle.kts domain/
COPY infrastructure/build.gradle.kts infrastructure/

# Descargar dependencias sin compilar el código (optimiza el tiempo de compilación posterior)
RUN ./gradlew build -x test --no-daemon || true

# 3. Copiar el código fuente de toda la arquitectura
COPY application/src application/src
COPY domain/src domain/src
COPY infrastructure/src infrastructure/src

# 4. Compilar el módulo de infraestructura (que arrastra a application y domain)
RUN ./gradlew :infrastructure:bootJar -x test --no-daemon

# Stage 2: Imagen final ligera para ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 5. Copiar el JAR generado (Spring Boot por defecto le añade la versión al nombre)
COPY --from=build /app/infrastructure/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]