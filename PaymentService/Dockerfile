FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/PaymentService
COPY build/libs/PaymentService-0.0.1-SNAPSHOT.jar /E-commerce-app/PaymentService/PaymentService-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/E-commerce-app/PaymentService/PaymentService-0.0.1-SNAPSHOT.jar"]