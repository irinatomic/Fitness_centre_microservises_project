package raf.fitness.reservation_servis.configuration;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RetryConfiguration {

    @Bean
    public Retry userServiceRetry() {
        RetryConfig config = RetryConfig.custom().maxAttempts(5).waitDuration(Duration.ofMillis(5000)).build();
        RetryRegistry registry = RetryRegistry.of(config);

        return registry.retry("userServiceRetry");
    }
}
