package com.ecommerceapp.eurekadiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryServiceApplication {

	//Eureka service for service discovery
	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryServiceApplication.class, args);
	}

}
