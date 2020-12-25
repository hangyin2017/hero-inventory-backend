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
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CrossOrigin
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderMapper salesOrderMapper;
    private final SalesOrderRepository salesOrderRepository;
    private final SoldItemRepository soldItemRepository;

    private final ItemRepository itemRepository;

    private SalesOrder findOneById(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order id=" + id + " does not exist"));
        return salesOrder;
    }

    private List<SalesOrderGetDto> fromEntity(List<SalesOrder> salesOrders) {
        return salesOrders.stream()
                .map(salesOrder -> salesOrderMapper.fromEntity(salesOrder))
                .collect(Collectors.toList());
    }

    public List<SalesOrderGetDto> getAll() {
        return fromEntity(salesOrderRepository.findAll());
    }

    public SalesOrderGetDto getOne(Long id) {
        return salesOrderMapper.fromEntity(findOneById(id));
    }

    @Transactional
    public Map<String, Object> addOne(SalesOrderPostDto salesOrderPostDto) {

        Map<String, Object> returnMap = new HashMap<>();

        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderPostDto);
        Set<SoldItem> soldItems = salesOrder.getSoldItems();

        if (soldItems.size() <= 0
                || soldItems.stream().mapToInt(item -> item.getQuantity()).sum() <= 0) {
            throw new RuntimeException("Can not create an order without any item");
        }

        salesOrder.setStatus("draft");
        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);

        soldItems.forEach((soldItem) -> {
            soldItem.setSalesOrder(savedOrder);

            Long id = soldItem.getItemId();
            Item item = itemRepository.findById(id).orElse(null);

            String name = item.getName();
            soldItem.setItemName(name);

            soldItemRepository.save(soldItem);
        });

        returnMap.put("code", 200);
        returnMap.put("data", salesOrderMapper.fromEntity(savedOrder));

        return returnMap;
    }

    @Transactional
    public SalesOrderGetDto update(Long Id, SalesOrderPutDto salesOrderPutDto) {
        SalesOrder salesOrder = salesOrderRepository.findById(Id).orElse(null);
        soldItemRepository.deleteBySalesOrder(salesOrder);
        if (salesOrder == null) {
            throw new RuntimeException("This salesorder does not exist");
        }

        if (!"draft".equals(salesOrder.getStatus())) {
            throw new RuntimeException("Only draft orders are editable");
        }

        salesOrderMapper.copy(salesOrderPutDto, salesOrder);
        SalesOrder saved = salesOrderRepository.save(salesOrder);
        Set<SoldItem> soldItems = salesOrder.getSoldItems().stream()
                .peek(e -> e.setSalesOrder(saved)).collect(Collectors.toSet());
        soldItemRepository.saveAll(soldItems);
        salesOrder.setSoldItems(soldItems);
        return salesOrderMapper.fromEntity(salesOrder);
    }

    @Transactional
    public void delete(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id).orElse(null);
        if (salesOrder == null) { throw new RuntimeException("This salesorder does not exist"); }

        String status = salesOrder.getStatus();
        if ("closed".equals(status)) { throw new RuntimeException("Cannot delete closed order"); }

        salesOrder.getSoldItems().forEach((soldItem) -> {
            Long itemId = soldItem.getItemId();
            Integer quantity = soldItem.getQuantity();

            if ("confirmed".equals(status)) {
                itemRepository.decreaseLockedStock(itemId, quantity);
            }

            soldItemRepository.delete(soldItem);
        });

        salesOrderRepository.deleteById(id);
    }

    @Transactional
    public Map<String, Object> confirm(Long id) {
        Map<String, Object> returnMap = new HashMap<>();

        SalesOrder salesOrder = findOneById(id);

        if (!salesOrder.getStatus().equals("draft")) {
            returnMap.put("code", 501);
            returnMap.put("msg", "Order's status must be draft");
            return returnMap;
        }

        List<Item> itemIdList = new ArrayList<Item>();
        salesOrder.getSoldItems().forEach((soldItem) -> {
            Long itemId = soldItem.getItemId();
            Integer quantity = soldItem.getQuantity();
            Item item = itemRepository.findById(itemId).orElse(null);
            Integer physicalStock = item.getPhysicalStock();
            Integer lockedStock = item.getLockedStock();
            Integer arrivingQuantity = item.getArrivingQuantity();

            if (quantity > physicalStock + arrivingQuantity - lockedStock) {
                itemIdList.add(item);
            }
        });

        if (itemIdList.size() == 0) {
            salesOrder.getSoldItems().forEach((salesdItem) -> {
                itemRepository.increaseLockedStock(salesdItem.getItemId(), salesdItem.getQuantity());
            });
            salesOrder.setStatus("confirmed");
            SalesOrder saved = salesOrderRepository.save(salesOrder);

            returnMap.put("code", 200);
            returnMap.put("data", salesOrderMapper.fromEntity(saved));
        } else {
            StringBuffer msg = new StringBuffer();

            msg.append("No enough items in stock.");
            msg.append("Current available stock of ");
            for (Item item : itemIdList) {
                msg.append("item ");
                msg.append(item.getId());
                msg.append(" is ");
                msg.append(item.getPhysicalStock() + item.getArrivingQuantity() - item.getLockedStock());
                msg.append(", ");
            }
            returnMap.put("code", 501);
            returnMap.put("msg", msg);
        }

        return returnMap;
    }

    @Transactional
    public Map<String, Object> send(Long id) {
        Map<String, Object> returnMap = new HashMap<>();

        SalesOrder salesOrder = findOneById(id);

        if (!salesOrder.getStatus().equals("confirmed")) {
            returnMap.put("code", 501);
            returnMap.put("msg", "Order's status must be confirmed");
            return returnMap;
        }

        List<Item> itemIdList = new ArrayList<Item>();
        salesOrder.getSoldItems().forEach((soldItem) -> {
            Long itemId = soldItem.getItemId();
            Integer quantity = soldItem.getQuantity();
            Item item = itemRepository.findById(itemId).orElse(null);
            Integer physicalStock = item.getPhysicalStock();

            if (quantity > physicalStock) {
                itemIdList.add(item);
            }
        });

        if (itemIdList.size() == 0) {
            salesOrder.getSoldItems().forEach((soldItem) -> {
                Long itemId = soldItem.getItemId();
                Integer quantity = soldItem.getQuantity();
                itemRepository.decreaseLockedStock(itemId, quantity);
                itemRepository.decreasePhysicalStock(itemId, quantity);
            });
            salesOrder.setStatus("closed");
            SalesOrder saved = salesOrderRepository.save(salesOrder);

            returnMap.put("code", 200);
            returnMap.put("data", salesOrderMapper.fromEntity(saved));
        } else {
            StringBuffer msg = new StringBuffer();

            msg.append("No enough items in stock.");
            msg.append("Current stock of ");
            for (Item item : itemIdList) {
                msg.append("item ");
                msg.append(item.getId());
                msg.append(" is ");
                msg.append(item.getPhysicalStock());
                msg.append(", ");
            }
            returnMap.put("code", 501);
            returnMap.put("msg", msg);
        }

        return returnMap;
    }

    public long getSalesOrderCount() {
        long SalesOrderCount = salesOrderRepository.count();
        return SalesOrderCount;
    }
}
