# --- Etapa 1: Build da aplicação ---
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código fonte
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dá permissão de execução pro maven wrapper e faz o build empacotando o jar
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# --- Etapa 2: Execução da aplicação ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia apenas o .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]