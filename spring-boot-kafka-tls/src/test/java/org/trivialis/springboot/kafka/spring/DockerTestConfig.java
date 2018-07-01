package org.trivialis.springboot.kafka.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/*
 * Profile used when running tests with docker.
 * 
 */
@Configuration
@PropertySource("classpath:application_test.properties")
@Profile("docker")
public class DockerTestConfig {
}
