package com.inventory.ims.config;

import com.inventory.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Health health() {
        try {
            long userCount = userRepository.count();
            return Health.up()
                    .withDetail("database", "Available")
                    .withDetail("userCount", userCount)
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "Unavailable")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}