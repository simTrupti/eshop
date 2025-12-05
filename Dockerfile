# Step 1: Use an official Java runtime as base image
FROM openjdk:21-jdk-slim

# Step 2: Set working directory in the container
WORKDIR /app

# Step 3: Copy the built JAR into the container
COPY target/eshop-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port the app runs on
EXPOSE 8080

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
