FROM eclipse-temurin:21
WORKDIR /app
COPY target/note-manager-0.0.1-SNAPSHOT.jar /app/note-manager-0.0.1-SNAPSHOT.jar
EXPOSE 9102
ENTRYPOINT ["java", "-jar", "/app/note-manager-0.0.1-SNAPSHOT.jar"]