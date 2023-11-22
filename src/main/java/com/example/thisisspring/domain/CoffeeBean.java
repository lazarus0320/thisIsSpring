package com.example.thisisspring.domain;

import com.example.thisisspring.dto.CoffeeBeanDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Setter 제거
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    @Builder // 생성자 위에 @Builder 적용
    public CoffeeBean(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }

    public CoffeeBeanDto toDto() {
        return CoffeeBeanDto.builder()
                .id(this.id)
                .name(this.name)
                .quantity(this.quantity)
                .build();
    }
}
