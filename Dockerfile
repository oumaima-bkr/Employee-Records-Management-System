# Step 1: Use a base image with Java pre-installed
FROM openjdk:21-jdk-slim AS build

# Step 2: Set the working directory
WORKDIR /app

# Step 3: Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Step 4: Build the application using Maven
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Step 5: Start a new lightweight container for the runtime
FROM openjdk:21-jdk-slim

# Step 6: Set the working directory
WORKDIR /app

# Step 7: Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Step 8: Expose the application's default port
EXPOSE 8080

# Step 9: Define the command to run the application
CMD ["java", "-jar", "app.jar"]
