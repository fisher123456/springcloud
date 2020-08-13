package com.cloud.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RoleManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoleManagerApplication.class, args);
	}

}
