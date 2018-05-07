package org.trivialis.log4j2.kafka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Log4j2KafkaTest {

    private Consumer consumer;

    @BeforeEach
    public void before() {

        String brokerUrl = System.getProperty("kafkaUrl", "localhost:9092");
        consumer = new Consumer(brokerUrl,
                "/etc/security/tls/client.truststore.jks",
                "salzburg",
                "/etc/security/tls/client.keystore.jks",
                "salzburg",
                "salzburg");

    }

    @AfterEach
    public void after() {
        consumer.close();
    }

    @Test
    void logToKafka() {
        String kafkaTopic = System.getProperty("kafkaTopic", "log4j2-kafka-topic");
        String message = "test log4j2 - kafka appender";
        new Log4j2Kafka().logToKafka(message);
        consumer.subscribeToTopic(kafkaTopic);
        List<String> messageList = consumer.getMessageList();
        assertEquals(1, messageList.size());
        assertTrue(messageList.get(0).contains(message));
    }
}
