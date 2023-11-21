package com.example.thisisspring.domain;

import com.example.thisisspring.dto.CoffeeBeanDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

// Setter 제거
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
//test
    @Builder // 생성자 위에 @Builder 적용
    public CoffeeBean(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }

    public CoffeeBeanDto toDto() {
        CoffeeBeanDto coffeeBeanDto = new CoffeeBeanDto();
        coffeeBeanDto.setId(this.id);
        coffeeBeanDto.setName(this.name);
        coffeeBeanDto.setQuantity(this.quantity);
        return coffeeBeanDto;
    }
}
