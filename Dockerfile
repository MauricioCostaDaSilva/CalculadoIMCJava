FROM maven:3.8.7-openjdk-18-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean compile assembly:single

FROM openjdk:18

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/bot-jar-with-dependencies.jar /app/bot-jar-with-dependencies.jar

CMD ["java", "-jar", "bot-jar-with-dependencies.jar"]
