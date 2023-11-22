package com.example.thisisspring.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeBeanDto {
    private Long id;
    private String name;
    private int quantity;

    @Builder
    public CoffeeBeanDto(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}
