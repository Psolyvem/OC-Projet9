FROM eclipse-temurin:21
WORKDIR /app
COPY target/discovery-service-0.0.1-SNAPSHOT.jar /app/discovery-service-0.0.1-SNAPSHOT.jar
EXPOSE 9500
ENTRYPOINT ["java", "-jar", "/app/discovery-service-0.0.1-SNAPSHOT.jar"]