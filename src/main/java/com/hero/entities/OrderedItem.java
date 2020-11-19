package com.hero.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ordered_items")
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_item_id")
    private Long orderedItemId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private SalesOrder salesOrder;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "total_quality")
    private Long totalQuality;

    @Column(name = "comments")
    private String comments;

    }
