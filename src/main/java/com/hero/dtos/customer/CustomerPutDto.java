package com.hero.dtos.customer;

import com.hero.entities.SalesOrder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CustomerPutDto {
    private Long id;
    private String name;
    private String companyName;
    private String website;
    private Boolean active;
    private String salutation;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date createdTime;
    private Date lastModifiedTime;
    private String comments;
    private Set<SalesOrder> salesOrders;
}