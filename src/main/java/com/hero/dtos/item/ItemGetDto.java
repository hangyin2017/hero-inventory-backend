package com.hero.dto.items;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ItemGetDto {

    private UUID id;
    private String code;
    private String name;
    private String unit;
    private String brand;
    private String category;
    private String supplier;
    private double weight;
    private double standardPrice;
    private double cost;
    private int quantity;
    private String remark;
    private Date dateOfAdd;
    private int shelfLife;
}
