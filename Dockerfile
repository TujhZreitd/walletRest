FROM openjdk:17-jdk-slim


WORKDIR /app


COPY target/walletRest-0.0.1-SNAPSHOT.jar /app/app.jar


ENTRYPOINT ["java", "-jar", "/app/app.jar"]