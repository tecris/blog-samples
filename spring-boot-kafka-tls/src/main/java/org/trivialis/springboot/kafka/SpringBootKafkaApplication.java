package org.trivialis.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootKafkaApplication implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(SpringBootKafkaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Running ...");
	}
}
