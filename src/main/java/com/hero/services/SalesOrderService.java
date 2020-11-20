package com.hero.services;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.entities.SalesOrder;
import com.hero.mappers.SalesOrderMapper;
import com.hero.repositories.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderMapper salesOrderMapper;
    private final SalesOrderRepository salesOrderRepository;


    private List<SalesOrderGetDto> fromEntity(List<SalesOrder> salesOrders) {
        return salesOrders.stream()
                .map(salesOrder -> salesOrderMapper.fromEntity(salesOrder))
                .collect(Collectors.toList());
    }

    public List<SalesOrderGetDto> getAllSalesOrders() {
        return fromEntity(salesOrderRepository.findAll());
    }

}
