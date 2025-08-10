FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/OrderService
COPY build/libs/OrderService-0.0.1-SNAPSHOT.jar /E-commerce-app/OrderService/OrderService-0.0.1-SNAPSHOT.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "/E-commerce-app/OrderService/OrderService-0.0.1-SNAPSHOT.jar"]