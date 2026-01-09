# ===== Stage 1: Build =====
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom trc de tan dung cache
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy source và build
COPY src ./src
RUN mvn -B clean package -DskipTests


# ===== Stage 2: Run =====
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy jar tu stage build
COPY --from=builder /app/target/*.jar app.jar

# Render se inject bien PORT
ENV JAVA_OPTS="-Xmx256m -Xms128m"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
