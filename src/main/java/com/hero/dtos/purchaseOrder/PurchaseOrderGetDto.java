package com.hero.dtos.purchaseOrder;

import com.hero.entities.PurchasedItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class PurchaseOrderGetDto {
    private Long Id;
    private String purchaseorderNumber;
    private String referenceNumber;
    private Date date;
    private String status;
    private Date shipmentDate;
    private String invoicedStatus;
    private String paidStatus;
    private String shippedStatus;
    private Date createdTime;
    private Date lastModifiedTime;
    private Long totalQuantity;
    private BigDecimal totalPrice;
    private String comments;
    private Boolean applyGst;
    private BigDecimal shipmentPrice;
    private BigDecimal adjustmentPrice;
    private Set<PurchasedItem> purchasedItems;
    private String supplier;
}
