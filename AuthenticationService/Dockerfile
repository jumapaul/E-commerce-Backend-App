FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/AuthenticationService
COPY build/libs/AuthenticationService-0.0.1-SNAPSHOT.jar /E-commerce-app/AuthenticationService/AuthenticationService-0.0.1-SNAPSHOT.jar
EXPOSE 8010
ENTRYPOINT ["java", "-jar", "/E-commerce-app/AuthenticationService/AuthenticationService-0.0.1-SNAPSHOT.jar"]