FROM eclipse-temurin:21
WORKDIR /app
COPY target/patient-manager-0.0.1-SNAPSHOT.jar /app/patient-manager-0.0.1-SNAPSHOT.jar
EXPOSE 9101
ENTRYPOINT ["java", "-jar", "/app/patient-manager-0.0.1-SNAPSHOT.jar"]