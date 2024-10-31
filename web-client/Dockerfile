FROM eclipse-temurin:21
WORKDIR /app
COPY target/web-client-0.0.1-SNAPSHOT.jar /app/web-client-0.0.1-SNAPSHOT.jar
EXPOSE 9200
ENTRYPOINT ["java", "-jar", "/app/web-client-0.0.1-SNAPSHOT.jar"]