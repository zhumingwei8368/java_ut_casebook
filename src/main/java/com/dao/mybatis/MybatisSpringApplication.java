package com.dao.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.dao.mybatis")
public class MybatisSpringApplication{

	public static void main(String[] args) {
		SpringApplication.run(MybatisSpringApplication.class, args);
		System.out.println("......Mybatis spring boot demo start successfully.......");
	}
}
