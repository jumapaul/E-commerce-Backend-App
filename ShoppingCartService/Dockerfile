FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/ShoppingCartService
COPY build/libs/ShoppingCartService-0.0.1-SNAPSHOT.jar /E-commerce-app/ShoppingCartService/ShoppingCartService-0.0.1-SNAPSHOT.jar
EXPOSE 8030
ENTRYPOINT ["java", "-jar", "/E-commerce-app/ShoppingCartService/ShoppingCartService-0.0.1-SNAPSHOT.jar"]