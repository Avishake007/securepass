package com.securepass.configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		  System.out.println("User Home Directory: " + System.getProperty("user.home"));
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
