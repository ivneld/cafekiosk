package com.example.cafekiosk.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SlowBeanConfig {

    record SlowBean() {

    }

//    @Bean
    public SlowBean slowBean() throws InterruptedException {
        int SLEEP_TIME = 3000;

        log.info(".... Configuring ....");
        Thread.sleep(SLEEP_TIME);
        log.info(".... slow ....");
        Thread.sleep(SLEEP_TIME);
        log.info(".... bean ....");
        Thread.sleep(SLEEP_TIME);
        log.info(".... Done! ....");
        return new SlowBean();
    }
}