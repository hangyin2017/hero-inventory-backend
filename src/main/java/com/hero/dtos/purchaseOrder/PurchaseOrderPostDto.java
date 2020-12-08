package com.hero.dtos.purchaseOrder;

import com.hero.entities.PurchasedItem;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PurchaseOrderPostDto {
    private Long purchaseorderId;
    private String purchaseorderNumber;
    private String referenceNumber;
    private Date date;
    //private String status;
    private Date shipmentDate;
    private String invoicedStatus;
    private String paidStatus;
    private String shippedStatus;
    private Date createdTime;
    private Date lastModifiedTime;
    private Long totalQuality;
    private String comments;
    private Set<PurchasedItem> purchasedItems;
}
