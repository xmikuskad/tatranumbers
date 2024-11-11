# Use an official OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set environment variables for the application
ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copy the JAR file from the build stage
COPY target/tatranumbers-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
