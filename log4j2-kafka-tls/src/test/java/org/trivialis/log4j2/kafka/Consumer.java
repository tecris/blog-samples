package org.trivialis.log4j2.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SslConfigs;

import java.util.*;

public class Consumer {

    private List<String> messageList = new ArrayList<>();
    private KafkaConsumer<String, String> consumer;

    Consumer(String brokerServer, String truststoreLocation,
                    String truststorePassword, String keystoreLocation,
                    String keystorePassword, String sslPassword) {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "log4j2.kafka-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        // ssl config
        props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        props.setProperty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
        props.setProperty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
        props.setProperty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);
        props.setProperty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
        props.setProperty(SslConfigs.SSL_KEY_PASSWORD_CONFIG, sslPassword);
        consumer = new KafkaConsumer<>(props);
    }

    void subscribeToTopic(final String topic) {

        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                messageList.add(record.value());
                // only interested in the first message
                return;
            }
        }
    }

    List<String> getMessageList() {
        return Collections.unmodifiableList(this.messageList);
    }

    void close() {
        consumer.close();
    }

}
