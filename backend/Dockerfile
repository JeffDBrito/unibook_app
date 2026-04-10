FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE ${BACKEND_PORT}

CMD ["java", "-jar", "target/app-0.0.1-SNAPSHOT.jar"]