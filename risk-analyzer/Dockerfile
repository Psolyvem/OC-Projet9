FROM eclipse-temurin:21
WORKDIR /app
COPY target/risk-analyzer-0.0.1-SNAPSHOT.jar /app/risk-analyzer-0.0.1-SNAPSHOT.jar
EXPOSE 9103
ENTRYPOINT ["java", "-jar", "/app/risk-analyzer-0.0.1-SNAPSHOT.jar"]