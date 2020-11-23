package com.hero.services;


import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.dtos.salesOrder.SalesOrderPutDto;
import com.hero.entities.SalesOrder;
import com.hero.entities.SoldItem;
import com.hero.mappers.SalesOrderMapper;
import com.hero.repositories.SalesOrderRepository;
import com.hero.repositories.SoldItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
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

    @Transactional
    public SalesOrderGetDto modifySalesOrder(Long salesorderId, SalesOrderPutDto salesOrderPutDto) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesorderId).orElse(null);
        soldItemRepository.deleteBySalesOrder(salesOrder);
        if (salesOrder == null){
            throw new RuntimeException("This salesorder is not exist.");
        }
        salesOrderMapper.copy(salesOrderPutDto, salesOrder);
        SalesOrder saved = salesOrderRepository.save(salesOrder);
        Set<SoldItem> soldItems = salesOrder.getSoldItems().stream().peek(e-> e.setSalesOrder(saved)).collect(Collectors.toSet());
        soldItemRepository.saveAll(soldItems);
        salesOrder.setSoldItems(soldItems);
        return salesOrderMapper.fromEntity(salesOrder);
    }

    @Transactional
    public void deleteSalesOrder(Long salesOrderId) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).orElse(null);
        if (salesOrder != null) {
            salesOrderRepository.deleteById(salesOrderId);
            for (SoldItem soldItem : salesOrder.getSoldItems()) {
                soldItemRepository.delete(soldItem);
            }
        }
    }
}
