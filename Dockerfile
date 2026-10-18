# --- Etapa 1: Build da aplicação ---
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código fonte
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Remove quebras de linha do Windows (CRLF) do mvnw, senão ele não executa no Linux
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# --- Etapa 2: Execução da aplicação ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia apenas o .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Limita a memória do Java a 75% da RAM do container (importante em planos pequenos, como 512 MB)
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:+UseSerialGC", "-jar", "app.jar"]