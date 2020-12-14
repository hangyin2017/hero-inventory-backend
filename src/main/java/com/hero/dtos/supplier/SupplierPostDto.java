package com.hero.dtos.supplier;

import lombok.Data;

import java.util.Date;

@Data
public class SupplierPostDto {
    private String supplierName;
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
}
