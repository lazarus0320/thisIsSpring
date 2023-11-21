package com.example.thisisspring.service;

import com.example.thisisspring.domain.CoffeeBean;
import com.example.thisisspring.repository.CoffeeBeanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CoffeeBeanServiceTest {

    @Autowired
    private CoffeeBeanRepository coffeeBeanRepository;

    @Test
    void createCoffeeBeanTest() {

        // given
        String name = "산토스";
        int quantity = 100;

        // when
        CoffeeBean coffee = CoffeeBean.builder()
                .name(name)
                .quantity(quantity)
                .build();

        coffeeBeanRepository.save(coffee);

        //then
//        assertThat(coffeeBeanRepository.findById(1L)).isPresent();
        CoffeeBean savedCoffee = coffeeBeanRepository.findByName(name)
                .orElseThrow(() -> new NullPointerException("해당 이름에 대한 CoffeeBean이 존재하지 않습니다."));

        assertThat(savedCoffee.getName()).isEqualTo(name);
        assertThat(savedCoffee.getQuantity()).isEqualTo(quantity);
    }
}
