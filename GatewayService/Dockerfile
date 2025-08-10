FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/GatewayService
COPY build/libs/GatewayService-0.0.1-SNAPSHOT.jar /E-commerce-app/GatewayService/GatewayService-0.0.1-SNAPSHOT.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "/E-commerce-app/GatewayService/GatewayService-0.0.1-SNAPSHOT.jar"]