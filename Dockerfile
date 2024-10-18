FROM maven:3.8.6-openjdk-17
WORKDIR /app
COPY . .
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8080
CMD ["mvn", "liquibase:update", "-Pdocker"]
CMD ["java", "-jar", "target/walletRest.jar"]