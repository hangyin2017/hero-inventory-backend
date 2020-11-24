package com.hero.dtos.item;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ItemPostDto {

    private String sku;
    private String upc;
    private String name;
    private String description;
    private Boolean active;
    private String category;
    private String brand;
    private String manufacturer;
    private BigDecimal sellingPrice;
    private BigDecimal costPrice;
    private Boolean applyGst;
    private Double length;
    private Double width;
    private Double height;
    private Double weight;
    private String unit;
    private Integer unitsPerCarton;
    private Integer unitsPerPallet;
    private Date createdTime;
    private Date lastModifiedTime;
    private Integer physicalStock;
    private Integer lockedStock;
    private Integer arrivingQuantity;
}

