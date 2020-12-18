package com.hero.dtos.customer;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerPostDto {
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
}
