# ==============================
# Etapa 1: Compilar la aplicación
# ==============================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar el JAR
RUN mvn clean package -DskipTests

# ==============================
# Etapa 2: Ejecutar la aplicación
# ==============================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Configurar el perfil activo (por defecto "dev")
ENV SPRING_PROFILES_ACTIVE=dev

# Puerto expuesto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
