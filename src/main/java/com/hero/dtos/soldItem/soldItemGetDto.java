package com.hero.dtos.soldItem;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class soldItemGetDto {
    private Long soldItemId;
    private Long itemId;
    private Long orderId;
    private Date createdTime;
    private Long quality;
    private BigDecimal rate;
    private String comments;
}
