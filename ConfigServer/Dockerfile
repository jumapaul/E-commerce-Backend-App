FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/ChatService
COPY build/libs/ConfigServer-0.0.1-SNAPSHOT.jar /E-commerce-app/ConfigServer/ConfigServer-0.0.1-SNAPSHOT.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/E-commerce-app/ConfigServer/ConfigServer-0.0.1-SNAPSHOT.jar"]