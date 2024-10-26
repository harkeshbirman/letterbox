FROM openjdk:21

WORKDIR /app

COPY build/libs/letterbox-0.0.1-SNAPSHOT.jar /app/letterbox-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "letterbox-0.0.1-SNAPSHOT.jar"]
