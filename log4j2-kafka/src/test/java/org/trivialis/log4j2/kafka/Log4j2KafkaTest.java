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

        consumer = new Consumer("localhost:9092",
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
        String message = "test junit 5 - spring boot";
        new Log4j2Kafka().logToKafka(message);
        consumer.subscribeToTopic("log4j2-kafka");
        List<String> messageList = consumer.getMessageList();
        assertEquals(1, messageList.size());
        assertTrue(messageList.get(0).contains(message));
    }
}