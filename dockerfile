# 1) Use an official Java image as the base (you can pick openjdk:17-jdk-slim, or similar)
FROM openjdk:17-jdk-slim

# 2) Install Tesseract OCR and any needed packages
RUN apt-get update && apt-get install -y --no-install-recommends \
    tesseract-ocr \
 && rm -rf /var/lib/apt/lists/*

# 3) Create a directory for your app
WORKDIR /app

# 4) Copy your Spring Boot JAR into the container
#    Make sure you have run `mvn clean package` so the JAR exists in target/
COPY target/expensetracker-0.0.1-SNAPSHOT.jar app.jar

# 5) Heroku sets PORT at runtime. We'll tell Spring Boot to use that port.
EXPOSE 8080
ENV PORT 8080

# 6) Run the Spring Boot JAR, binding to the assigned $PORT
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]