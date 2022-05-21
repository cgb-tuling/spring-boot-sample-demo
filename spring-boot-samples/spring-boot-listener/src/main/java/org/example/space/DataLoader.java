package org.example.space;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        log.info("Loading data...");
    }
}