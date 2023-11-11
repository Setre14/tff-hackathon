FROM openjdk:17-jdk as builder

WORKDIR /home/java/server

COPY . .
RUN microdnf install findutils
RUN ./gradlew build

# ------
FROM openjdk:17-jdk
WORKDIR /home/java/server

COPY --from=builder /home/java/server/build/libs .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "visitoranalytics-0.0.1-SNAPSHOT.jar"]