FROM openjdk:17-jdk-alpine
WORKDIR /E-commerce-app/ProductCatalogService
COPY build/libs/ProductCatalogService-0.0.1-SNAPSHOT.jar /E-commerce-app/ProductCatalogService/ProductCatalogService-0.0.1-SNAPSHOT.jar
EXPOSE 8020
ENTRYPOINT ["java", "-jar", "/E-commerce-app/ProductCatalogService/ProductCatalogService-0.0.1-SNAPSHOT.jar"]