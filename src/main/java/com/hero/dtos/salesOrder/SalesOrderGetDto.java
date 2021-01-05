package com.hero.dtos.salesOrder;

import com.hero.entities.SoldItem;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SalesOrderGetDto {
    private Long Id;
    private String salesorderNumber;
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
    private Long totalPrice;
    private String comments;
    private Boolean applyGst;
    private Long shipmentPrice;
    private Long adjustmentPrice;
    private Set<SoldItem> soldItems;
    private String customer;
}
