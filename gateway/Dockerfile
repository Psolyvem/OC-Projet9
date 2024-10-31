FROM eclipse-temurin:21
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar /app/gateway-0.0.1-SNAPSHOT.jar
EXPOSE 9100
ENTRYPOINT ["java", "-jar", "/app/gateway-0.0.1-SNAPSHOT.jar"]