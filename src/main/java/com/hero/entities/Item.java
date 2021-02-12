package com.hero.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "upc")
    private String upc;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "unit")
    private String unit;

    @Column(name = "units_per_carton")
    private Integer unitsPerCarton;

    @Column(name = "units_per_pallet")
    private Integer unitsPerPallet;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Column(name = "physical_stock")
    private Integer physicalStock;

    @Column(name = "locked_stock")
    private Integer lockedStock;

    @Column(name = "arriving_quantity")
    private Integer arrivingQuantity;

    @Column(name = "average_cost")
    private BigDecimal averageCost;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resource> images;
}


