package org.trivialis.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	public static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

	private String message;

	@KafkaListener(topics = "${kafka.topic}")
	public void listenForTickerMessages(@Payload String message) {
		LOGGER.info("Message received: {} ", message);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
