package com.hero.dtos.customer;

import lombok.Data;

@Data
public class CustomerPutDto {
    private Long customerId;
    private String customerName;
}