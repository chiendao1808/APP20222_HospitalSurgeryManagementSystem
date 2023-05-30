package com.app20222.app20222_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class App20222BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(App20222BackendApplication.class, args);
    }

}
