package com.suma.consumer.config.asyncconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/* Following configuration class is used for asynchronous task
 * e.g If we want to send three mail to three user at a time in such case we use AsynConfig
 * The class we have to make async class we need to annotate it with @Async */

@Configuration
@EnableAsync
public class AsynConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); //---- min no of thread
        executor.setMaxPoolSize(10); //---- max no of thread
        executor.setQueueCapacity(100); // Queue size
        executor.setThreadNamePrefix("Cd Thread -");  // Thread Name
        executor.initialize();   //Initialization of thread
        return executor;
    }
}
