package com.jyujyu.dayonetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class DayoneTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayoneTestApplication.class, args);
	}

}
