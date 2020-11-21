package com.hero.dtos.soldItem;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SoldItemGetDto {
    private Long soldItemId;
    private Long itemId;
    private Long quality;
    private BigDecimal rate;
}
