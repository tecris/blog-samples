package org.trivialis.springboot.kafka.spring;

import org.springframework.test.context.ActiveProfilesResolver;

public class StringBootKafkaActiveProfilesResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> arg0) {
        String activeProfile = System.getProperty("spring.profiles.active", "docker");
        System.out.println("Spring active profile: " + activeProfile);
        return new String[] { activeProfile };
    }

}
