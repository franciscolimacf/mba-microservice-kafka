# ── Stage 1: build ────────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copia POMs antes do source para maximizar cache de dependências:
# essa camada só é invalidada quando um pom.xml muda.
COPY pom.xml .
COPY order-service/pom.xml order-service/pom.xml
COPY notification-service/pom.xml notification-service/pom.xml

RUN mvn dependency:go-offline --no-transfer-progress -q

# Copia apenas o source deste módulo e compila
COPY order-service/src order-service/src
RUN mvn package -pl order-service -DskipTests --no-transfer-progress -q

# ── Stage 2: runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/order-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]