package com.hichat.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HichatEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HichatEurekaApplication.class, args);
	}
}
