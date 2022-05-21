package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.liquibase.House;
import org.example.liquibase.HouseRepository;
import org.example.liquibase.Item;
import org.example.liquibase.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SpringBootLiquibaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLiquibaseApplication.class, args);
    }


    @Bean
    CommandLineRunner init(HouseRepository houseRepository, ItemRepository itemRepository) {
        return args -> {
            log.info("Saving house...");
            House house = new House();
            house.setOwner("Julius Krah");
            house.setFullyPaid(true);
            houseRepository.save(house);
            log.info("Saved house instance: {}", house);

            log.info("Saving item in house");
            Item item = new Item();
            item.setName("Washing machine");
            item.setHouse(house);
            itemRepository.save(item);
            log.info("Saved item {} in house", item);
        };
    }
}

