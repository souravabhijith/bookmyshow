package com.example.bookmyshow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by AbhijithRavuri.
 */
@Configuration
public class ScheduledExecutorConfig {

    @Bean
    public ScheduledExecutorService threadPoolTaskScheduler(){
        ScheduledExecutorService scheduledExecutorService =  Executors.newScheduledThreadPool(10);
        return scheduledExecutorService;
    }
}
