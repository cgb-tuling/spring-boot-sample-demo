package org.example.liquibase;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DataJpaTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
public class ItemRepositoryTest {
    private final TestEntityManager em;
    private final ItemRepository itemRepository;

    @BeforeEach
    void before() {
        House house = new House();
        house.setOwner("James Tuffour");
        house.setFullyPaid(true);
        em.persist(house);

        Item item = new Item();
        item.setName("Washing machine");
        item.setHouse(em.getEntityManager().getReference(House.class, 1));
        em.persist(item);
    }

    @Test
    @DisplayName("find item by id")
    void testFindById() {
        Optional<Item> item = itemRepository.findById(2);
        Condition condition = new Condition<Item>(i -> "Washing machine".equals(i.getName()), "Name matches 'Washing Machine'");
        assertThat(item).isPresent();
        assertThat(item).hasValueSatisfying(condition);
    }
}
