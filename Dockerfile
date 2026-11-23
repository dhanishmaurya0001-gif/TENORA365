FROM node:22-alpine AS frontend
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ ./
ENV VITE_DEMO_MODE=true VITE_API_URL=/api
RUN npm run build

FROM maven:3.9.11-eclipse-temurin-21 AS backend
WORKDIR /backend
COPY backend/pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline
COPY backend/src ./src
COPY --from=frontend /frontend/dist ./src/main/resources/static
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=backend /backend/target/tenora365-1.0.0.jar app.jar
ENV SPRING_PROFILES_ACTIVE=demo JAVA_TOOL_OPTIONS="-Xmx256m -XX:MaxMetaspaceSize=160m" STORAGE_DIR=/tmp/tenora-demo-storage
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
