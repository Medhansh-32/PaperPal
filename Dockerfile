# Step 1: Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the jar file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Step 4: Expose the port that your Spring Boot app will run on
EXPOSE 8080

# Step 5: Define the command to run the Spring Boot application
CMD ["java", "-jar", "demo.jar"]
