package org.example.liquibase;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

//@AutoConfigureTestDatabase
//@DataJpaTest
//@RequiredArgsConstructor
//@TestConstructor(autowireMode = AutowireMode.ALL)
@SpringBootTest
public class HouseRepositoryTest {
    @Autowired
    private HouseRepository houseRepository;

    @BeforeEach
    void before() {
        House house = new House();
        house.setOwner("Julius Krah");
        house.setFullyPaid(true);
        houseRepository.save(house);
    }

    @Test
    @DisplayName("find house by id")
    void testFindById() {
        Optional<House> house = houseRepository.findById(1);
        Condition condition = new Condition<House>(h -> h.isFullyPaid(), "Is fully paid");
        assertThat(house).isPresent();
        assertThat(house).hasValueSatisfying(condition);
    }
}
