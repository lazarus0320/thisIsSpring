package com.example.thisisspring.service;

import com.example.thisisspring.domain.CoffeeBean;
import com.example.thisisspring.dto.CoffeeBeanDto;
import com.example.thisisspring.repository.CoffeeBeanRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CoffeeBeanService {

    private final CoffeeBeanRepository coffeeBeanRepository;

    public CoffeeBeanService(CoffeeBeanRepository coffeeBeanRepository) {
        this.coffeeBeanRepository = coffeeBeanRepository;
    }

    @PostConstruct
    public void saveTenCoffeeBeansEfficient() {
        List<CoffeeBean> coffeeBeans = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            String coffeeName = "커피 이름" + i;
            int quantity = 100;
            CoffeeBean coffeeBean = new CoffeeBean(coffeeName, quantity);
            coffeeBeans.add(coffeeBean);
        }

        coffeeBeanRepository.saveAll(coffeeBeans);
    }

    public List<CoffeeBeanDto> getAllCoffeeBeansDto() {
        List<CoffeeBean> coffeeBeans = coffeeBeanRepository.findAll();

        // CoffeeBean을 CoffeeBeanDto로 변환하여 리스트로 반환
        return coffeeBeans.stream()
                .map(CoffeeBean::toDto)
                .toList();
    }

    public void updateCoffeeBeanQuantity(String name, int quantityToAdd) {
        try {
            CoffeeBean coffeeBean = coffeeBeanRepository.findByName(name)
                    .orElseThrow(() -> new EntityNotFoundException("해당 이름의 커피 데이터를 찾을 수 없습니다."));

            coffeeBean.updateQuantity(quantityToAdd);

            coffeeBeanRepository.save(coffeeBean);

            log.info("{} 커피 데이터의 재고가 {}만큼 업데이트 되었습니다.", name, quantityToAdd);
        } catch (EntityNotFoundException e) {
            log.warn("커피 데이터 업데이트 중 오류: 해당 이름의 커피 데이터를 찾을 수 없습니다.", e);
            throw e;
        } catch (Exception e) {
            log.error("커피 데이터 업데이트 중 오류 발생", e);
            throw new RuntimeException("커피 데이터 재고 업데이트 중에 오류가 발생했습니다.", e);
        }
    }

}
