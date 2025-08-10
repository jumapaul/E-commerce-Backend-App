FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/EurekaDiscoveryService
COPY build/libs/EurekaDiscoveryService-0.0.1-SNAPSHOT.jar /E-commerce-app/EurekaDiscoveryService/EurekaDiscoveryService-0.0.1-SNAPSHOT.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "/E-commerce-app/EurekaDiscoveryService/EurekaDiscoveryService-0.0.1-SNAPSHOT.jar"]