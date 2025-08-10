FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/ChatService
COPY build/libs/ChatService-0.0.1-SNAPSHOT.jar /E-commerce-app/ChatService/ChatService-0.0.1-SNAPSHOT.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "/E-commerce-app/ChatService/ChatService-0.0.1-SNAPSHOT.jar"]