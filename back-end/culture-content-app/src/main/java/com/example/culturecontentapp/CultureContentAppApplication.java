package com.example.culturecontentapp;

import com.example.culturecontentapp.storage.StorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CultureContentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureContentAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
