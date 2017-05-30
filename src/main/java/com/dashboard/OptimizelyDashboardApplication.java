package com.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OptimizelyDashboardApplication {

    public static void main(String args[]) {
        SpringApplication.run(OptimizelyDashboardApplication.class, args);
    }




}
