package com.securepass.user_service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

	@Bean
	 ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
