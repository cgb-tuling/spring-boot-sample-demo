package org.example.quartz.memory.job;

import org.springframework.stereotype.Component;

@Component
public class MyBootMethodJob {

    public void exe() {
        System.out.println("~~ MyBootMethodJob ~~");
    }
}