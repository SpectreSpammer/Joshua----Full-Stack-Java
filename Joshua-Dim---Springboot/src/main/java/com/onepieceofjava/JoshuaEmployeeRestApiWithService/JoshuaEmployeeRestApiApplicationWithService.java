package com.onepieceofjava.JoshuaEmployeeRestApiWithService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.onepieceofjava.JoshuaEmployeeRestApi.controller",
		"com.onepieceofjava.JoshuaEmployeeRestApi.service",
		"com.onepieceofjava.JoshuaEmployeeRestApi.repository"
})
@EntityScan("com.onepieceofjava.JoshuaEmployeeRestApi.model")
@EnableJpaRepositories("com.onepieceofjava.JoshuaEmployeeRestApi.repository")
public class JoshuaEmployeeRestApiApplicationWithService {

	public static void main(String[] args) {
		SpringApplication.run(JoshuaEmployeeRestApiApplicationWithService.class, args);
	}

}
