package com.dao.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dao.jpa"})
public class JpaSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaSpringApplication.class, args);
		System.out.println("......JPA spring boot demo start successfully.......");
	}
}
