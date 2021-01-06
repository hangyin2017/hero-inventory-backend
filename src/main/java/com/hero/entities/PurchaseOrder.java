package com.hero.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "purchaseorders")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchaseorder_id")
    private Long Id;

    @Column(name = "purchaseorder_number")
    private String purchaseorderNumber;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "shipment_date")
    private Date shipmentDate;

    @Column(name = "invoiced_status")
    private String invoicedStatus;

    @Column(name = "paid_status")
    private String paidStatus;

    @Column(name = "shipped_status")
    private String shippedStatus;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "comments")
    private String comments;

    @Column(name = "apply_gst")
    private Boolean applyGst;

    @Column(name = "shipment_price")
    private BigDecimal shipmentPrice;

    @Column(name = "adjustment_price")
    private BigDecimal adjustmentPrice;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PurchasedItem> purchasedItems;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supplier supplier;
}
