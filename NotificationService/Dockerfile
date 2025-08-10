FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/NotificationService
COPY build/libs/NotificationService-0.0.1-SNAPSHOT.jar /E-commerce-app/NotificationService/NotificationService-0.0.1-SNAPSHOT.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "/E-commerce-app/NotificationService/NotificationService-0.0.1-SNAPSHOT.jar"]