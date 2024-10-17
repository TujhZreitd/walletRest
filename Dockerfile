FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/wallet-rest-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]