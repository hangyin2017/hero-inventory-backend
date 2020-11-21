package com.hero.services;


import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.entities.SalesOrder;
import com.hero.entities.SoldItem;
import com.hero.mappers.SalesOrderMapper;
import com.hero.repositories.SalesOrderRepository;
import com.hero.repositories.SoldItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderMapper salesOrderMapper;
    private final SalesOrderRepository salesOrderRepository;
    private final SoldItemRepository soldItemRepository;


    private List<SalesOrderGetDto> fromEntity(List<SalesOrder> salesOrders) {
        return salesOrders.stream()
                .map(salesOrder -> salesOrderMapper.fromEntity(salesOrder))
                .collect(Collectors.toList());
    }

    public List<SalesOrderGetDto> getAllSalesOrders() {
        return fromEntity(salesOrderRepository.findAll());
    }

    @Transactional
    public SalesOrderGetDto addSalesOrder(SalesOrderPostDto salesOrderPostDto) {
        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderPostDto);
        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);
        for(SoldItem soldItem : salesOrder.getSoldItems()) {
            soldItem.setSalesOrder(savedOrder);
            soldItemRepository.save(soldItem);
        }
        return salesOrderMapper.fromEntity(savedOrder);
    }

}
