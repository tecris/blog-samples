package org.trivialis.log4j2.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Kafka {

    private static final Logger logger = LogManager.getLogger(Log4j2Kafka.class.getName());


    public static void main(String[] args){

        new Log4j2Kafka().logToKafka("Log4j2 => Hello Kafka.1");
    }

    public void logToKafka(String logMessage) {

        logger.debug(logMessage + " -- debug log");
        logger.info(logMessage + " -- info log");
        logger.warn(logMessage + " -- warn log");
        logger.error(logMessage + " -- error log");
    }
}
