package com.pragma.plazadecomidas.authservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.NumberFormat;

@SpringBootApplication
@Slf4j
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	public static void printInfo() {

		Runtime runtime = Runtime.getRuntime();
		final NumberFormat format = NumberFormat.getInstance();
		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();
		final long mb = 1024L * 1024L;
		final String mega = " MB";

		log.info("========================== Memory Info ==========================");
		log.info("Free memory: {}", format.format(freeMemory / mb) + mega);
		log.info("Allocated memory: {}", format.format(allocatedMemory / mb) + mega);
		log.info("Max memory: {}", format.format(maxMemory / mb) + mega);
		log.info("Total free memory: {}", format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
		log.info("=================================================================");
		log.info("");

	}

	@Bean
	public CommandLineRunner memInfoRunner() {
		return args -> printInfo();
	}

}
