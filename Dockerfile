# Use a base image with Java 21
FROM openjdk:21-jdk-oracle

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY ./target/geolocalizacion-ips-0.0.1-SNAPSHOT.jar /app

# Expose the port that the Spring Boot application runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "geolocalizacion-ips-0.0.1-SNAPSHOT.jar"]
