package com.beantimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */


@SpringBootApplication
//@ComponentScan({"com.beantimer"})
//@EntityScan("com.beantimer.entity")
//@EnableJpaRepositories("com.beantimer.repo")
//@EnableTransactionManagement
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}            