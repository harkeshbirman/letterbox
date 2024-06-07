package com.harkesh.letterbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LetterboxApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LetterboxApplication.class, args);
	}

}

