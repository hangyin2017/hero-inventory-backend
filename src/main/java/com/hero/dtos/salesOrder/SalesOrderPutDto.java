package com.hero.dtos.salesOrder;

import com.hero.entities.SoldItem;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SalesOrderPutDto {
    private Long salesorderId;
    private String salesorderNumber;
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
    private Set<SoldItem> soldItems;
}
