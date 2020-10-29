package com.hero.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

//    @Column(name = "unit")
//    private String unit;

    @Column(name = "brand")
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "standard_price")
    private BigDecimal standardPrice;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "remark")
    private String remark;

    @Column(name = "date_of_add")
    private Date timeOfAdd;

    @Column(name = "shelf_life")
    private Integer shelfLife;
}
