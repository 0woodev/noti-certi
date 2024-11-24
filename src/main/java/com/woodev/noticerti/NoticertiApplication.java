package com.woodev.noticerti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NoticertiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticertiApplication.class, args);
    }

}
