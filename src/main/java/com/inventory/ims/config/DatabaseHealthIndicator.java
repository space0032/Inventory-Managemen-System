package com.inventory.ims.config;

import com.inventory.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator {

    @Autowired
    private UserRepository userRepository;

    public boolean isDatabaseHealthy() {
        try {
            userRepository.count();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getHealthStatus() {
        try {
            long userCount = userRepository.count();
            return "Database: Available, Users: " + userCount;
        } catch (Exception e) {
            return "Database: Unavailable - " + e.getMessage();
        }
    }
}