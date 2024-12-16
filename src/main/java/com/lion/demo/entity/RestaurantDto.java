package com.lion.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDto {
    private int infoCount;
    private int reviewCount;
    private double reviewScore;
    private float matchScore;

    private Restaurant restaurant;

}
