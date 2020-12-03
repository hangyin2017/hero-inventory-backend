package com.hero.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "purchased_items")
@JsonIgnoreProperties("purchaseOrder")
public class PurchasedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchased_item_id")
    private Long purchasedItemId;

    @Column(name = "item_id")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "purchaseorder_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "rate")
    private BigDecimal rate;

//    @Override
//    public String toString() {
//        return "SoldItem{" +
//                "soldItemId=" + soldItemId +
//                ", itemId=" + itemId +
//                ", quality=" + quality +
//                ", rate=" + rate +
//                '}';
//    }
}
