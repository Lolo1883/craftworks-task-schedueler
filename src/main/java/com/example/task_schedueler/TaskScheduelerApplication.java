package com.example.task_schedueler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskScheduelerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskScheduelerApplication.class, args);
    }


}


