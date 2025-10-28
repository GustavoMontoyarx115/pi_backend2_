# ==============================
# Etapa 1: Compilar la aplicación
# ==============================
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar solo pom.xml para aprovechar cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar el JAR (sin tests)
RUN mvn clean package -DskipTests

# ==============================
# Etapa 2: Ejecutar la aplicación
# ==============================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Configurar el perfil activo (por defecto "dev")
ENV SPRING_PROFILES_ACTIVE=dev

# Variables de entorno configurables
ENV SERVER_PORT=8080
ENV JAVA_OPTS=""

# Puerto expuesto
EXPOSE 8080

# Comando para ejecutar la aplicación (soporta opciones dinámicas)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
