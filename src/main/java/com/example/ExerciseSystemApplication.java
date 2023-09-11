package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ExerciseSystemApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/ExerciseSystem-0.0.1-SNAPSHOT");
		SpringApplication.run(ExerciseSystemApplication.class, args);
	}
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(ExerciseSystemApplication.class);
	    }	
}
