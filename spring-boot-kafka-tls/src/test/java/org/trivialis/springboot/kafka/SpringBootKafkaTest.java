package org.trivialis.springboot.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.trivialis.springboot.kafka.spring.DockerTestConfig;
import org.trivialis.springboot.kafka.spring.StringBootKafkaActiveProfilesResolver;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringBootKafkaApplication.class})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { DockerTestConfig.class })
@ActiveProfiles(resolver =StringBootKafkaActiveProfilesResolver.class)
class SpringBootKafkaTest {
    
	private static final Integer MAX_NO_OF_RETRIES = 20;
	private static final Integer WAIT_TIME_SECONDS = 1;
	
    @Autowired
    private Consumer consumer;
    
    @Autowired
    private Producer producer;

    @Test
    void logToKafka() throws InterruptedException {
    	int retryCounter = 0;

    	final String expectedMessage = "test";
    	producer.sendMessage(expectedMessage);

    	while(retryCounter++ < MAX_NO_OF_RETRIES && consumer.getMessage() == null) {
    		TimeUnit.SECONDS.sleep(WAIT_TIME_SECONDS);
    	}
    	assertEquals(expectedMessage, consumer.getMessage());
    }
}
