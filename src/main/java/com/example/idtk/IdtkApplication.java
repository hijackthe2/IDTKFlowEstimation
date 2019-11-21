package com.example.idtk;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class IdtkApplication {

	private static Logger logger = Logger.getLogger(IdtkApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IdtkApplication.class, args);
		logger.info("~~running~~ " + LocalDateTime.now());
	}

}
