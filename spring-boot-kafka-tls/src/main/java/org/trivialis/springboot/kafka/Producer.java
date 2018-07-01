package org.trivialis.springboot.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	private final KafkaTemplate kafkaTemplate;

	@Autowired
	public Producer(KafkaTemplate kafkaTemplate, @Value("${kafka.topic}") String topic) {
		this.kafkaTemplate = kafkaTemplate;
		this.kafkaTemplate.setDefaultTopic(topic);
	}

	public void sendMessage(String message) {
		this.kafkaTemplate.sendDefault(message);
	}
}
