package com.example.baidoxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BaidoxeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaidoxeApplication.class, args);
    }
}
