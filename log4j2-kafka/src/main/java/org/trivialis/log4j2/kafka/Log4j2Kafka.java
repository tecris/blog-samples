package org.trivialis.log4j2.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Kafka {

    private static final Logger logger = LogManager.getLogger(Log4j2Kafka.class.getName());

    public static void main(String[] args){

        logger.debug("Log4j2 => Hello Kafka - debug log");
        logger.info("Log4j2 => Hello Kafka -- info log");
        logger.warn("Log4j2 => Hello Kafka -- warn log");
        logger.error("Log4j2 => Hello Kafka -- error log");
    }
}
