package com.hero.services;


import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.dtos.salesOrder.SalesOrderPutDto;
import com.hero.entities.Item;
import com.hero.entities.SalesOrder;
import com.hero.entities.SoldItem;
import com.hero.mappers.SalesOrderMapper;
import com.hero.repositories.ItemRepository;
import com.hero.repositories.SalesOrderRepository;
import com.hero.repositories.SoldItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderMapper salesOrderMapper;
    private final SalesOrderRepository salesOrderRepository;
    private final SoldItemRepository soldItemRepository;

    private final ItemRepository itemRepository;


    private List<SalesOrderGetDto> fromEntity(List<SalesOrder> salesOrders) {
        return salesOrders.stream()
                .map(salesOrder -> salesOrderMapper.fromEntity(salesOrder))
                .collect(Collectors.toList());
    }

    public List<SalesOrderGetDto> getAllSalesOrders() {
        return fromEntity(salesOrderRepository.findAll());
    }

    @Transactional
    public Map<String, Object> addSalesOrder(SalesOrderPostDto salesOrderPostDto) {

        Map<String, Object> returnMap = new HashMap<>();

        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderPostDto);

        List<Item> itemIdList = new ArrayList<Item>();
        for (SoldItem soldItem : salesOrder.getSoldItems()) {

            Long id = soldItem.getItemId();
            Long quantity = soldItem.getQuantity();

            Item item = itemRepository.findById(id).orElse(null);
            if (item.getPhysicalStock() < quantity) {
                itemIdList.add(item);
            }
        }

        if (itemIdList.size() == 0) {
            SalesOrder savedOrder = salesOrderRepository.save(salesOrder);

            for (SoldItem soldItem : salesOrder.getSoldItems()) {
                soldItem.setSalesOrder(savedOrder);
                soldItemRepository.save(soldItem);
                itemRepository.decreaseItemStock(soldItem.getItemId(), soldItem.getQuantity());
            }
            returnMap.put("code", 200);
            returnMap.put("data", salesOrderMapper.fromEntity(savedOrder));
        } else {
            StringBuffer msg = new StringBuffer();

            msg.append("Current stock of ");
            for (int i = 0; i < itemIdList.size(); i++) {
                msg.append("item ");
                msg.append(itemIdList.get(i).getId());
                msg.append(" is ");
                msg.append(itemIdList.get(i).getPhysicalStock());
                msg.append(", ");
            }
            msg.append("please delete this item or change the number of quantity");
            returnMap.put("code", 501);
            returnMap.put("msg", msg);
        }

        return returnMap;
    }

    @Transactional
    public SalesOrderGetDto modifySalesOrder(Long salesorderId, SalesOrderPutDto salesOrderPutDto) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesorderId).orElse(null);
        soldItemRepository.deleteBySalesOrder(salesOrder);
        if (salesOrder == null) {
            throw new RuntimeException("This salesorder is not exist.");
        }
        salesOrderMapper.copy(salesOrderPutDto, salesOrder);
        SalesOrder saved = salesOrderRepository.save(salesOrder);
        Set<SoldItem> soldItems = salesOrder.getSoldItems().stream().peek(e -> e.setSalesOrder(saved)).collect(Collectors.toSet());
        soldItemRepository.saveAll(soldItems);
        salesOrder.setSoldItems(soldItems);
        return salesOrderMapper.fromEntity(salesOrder);
    }

    @Transactional
    public void deleteSalesOrder(Long salesOrderId) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).orElse(null);
        if (salesOrder == null) {
            throw new RuntimeException("This salesorder is not exist.");
        } else {
            salesOrderRepository.deleteById(salesOrderId);
            for (SoldItem soldItem : salesOrder.getSoldItems()) {
                soldItemRepository.delete(soldItem);
            }
        }
    }
}
