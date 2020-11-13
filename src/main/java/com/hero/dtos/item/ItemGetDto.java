package com.hero.dtos.item;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ItemGetDto {

    private Long id;
    private String sku;
    private String name;
    private String unit;
    //private String brand;
    //private String category;
    //private String supplier;
    //private Double weight;
    //private BigDecimal standardPrice;
    //private BigDecimal cost;
    //private Integer quantity;
    //private String remark;
    //private Date timeOfAdd;
    //private String shelfLife;
}
