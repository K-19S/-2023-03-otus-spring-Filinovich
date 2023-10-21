package ru.otus.filinovich.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OsHealthCheck implements HealthIndicator {
    @Override
    public Health health() {
        String os = System.getProperty("os.name");
        if ("Windows 10".equals(os)) {
            return Health.up().build();
        } else {
            return Health.down()
                    .withDetail("message", "ОС: " + os + ". Протестировано для Windows 10")
                    .build();
        }
    }
}
