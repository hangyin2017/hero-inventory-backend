package com.hero.services;

import com.hero.dtos.purchaseOrder.PurchaseOrderGetDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPostDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPutDto;
import com.hero.entities.Item;
import com.hero.entities.PurchaseOrder;
import com.hero.entities.PurchasedItem;
import com.hero.mappers.PurchaseOrderMapper;
import com.hero.repositories.ItemRepository;
import com.hero.repositories.PurchaseOrderRepository;
import com.hero.repositories.PurchasedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchasedItemRepository purchasedItemRepository;

    private final ItemRepository itemRepository;

    private PurchaseOrder findOneById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order id=" + id + " does not exist"));
        return purchaseOrder;
    }

    private List<PurchaseOrderGetDto> fromEntityList(List<PurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream()
                .map(purchaseOrder -> purchaseOrderMapper.fromEntity(purchaseOrder))
                .collect(Collectors.toList());
    }

    public List<PurchaseOrderGetDto> getAll(){
        return fromEntityList(purchaseOrderRepository.findAll());
    };

    public PurchaseOrderGetDto getOne(Long id) {
        return purchaseOrderMapper.fromEntity(findOneById(id));
    }

    @Transactional
    public Map<String, Object> addOne(PurchaseOrderPostDto purchaseOrderPostDto) {

        Map<String, Object> returnMap = new HashMap<>();

        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(purchaseOrderPostDto);

        List<Item> itemIdList = new ArrayList<Item>();
        for (PurchasedItem purchasedItem : purchaseOrder.getPurchasedItems()) {

            Long id = purchasedItem.getItemId();
            Integer quantity = purchasedItem.getQuantity();

            Item item = itemRepository.findById(id).orElse(null);
            String name = item.getName();

            purchasedItem.setItemName(name);
            
            if (quantity > 0) {
                itemIdList.add(item);
            }
        }

        if (itemIdList.size() <= 0) {
            throw new RuntimeException("Can not create an order without any item");
        }

        if (itemIdList.size() > 0) {
            purchaseOrder.setStatus("draft");
            PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);

            for (PurchasedItem purchasedItem : purchaseOrder.getPurchasedItems()) {
                purchasedItem.setPurchaseOrder(savedOrder);
                purchasedItemRepository.save(purchasedItem);
            }
            returnMap.put("code", 200);
            returnMap.put("data", purchaseOrderMapper.fromEntity(savedOrder));
        } else {
            StringBuffer msg = new StringBuffer();

            msg.append("Can not create an order without any item");
            returnMap.put("code", 501);
            returnMap.put("msg", msg);
        }

        return returnMap;
    }

    @Transactional
    public PurchaseOrderGetDto update(Long id, PurchaseOrderPutDto purchaseOrderPutDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        purchasedItemRepository.deleteByPurchaseOrder(purchaseOrder);
        if (purchaseOrder == null) {
            throw new RuntimeException("This purchaseorder does not exist");
        }

        if (!"draft".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Only draft orders are editable");
        }

        purchaseOrderMapper.copy(purchaseOrderPutDto, purchaseOrder);
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);
        Set<PurchasedItem> purchasedItems = purchaseOrder.getPurchasedItems().stream()
                .peek(e -> e.setPurchaseOrder(saved)).collect(Collectors.toSet());
        purchasedItemRepository.saveAll(purchasedItems);
        purchaseOrder.setPurchasedItems(purchasedItems);
        return purchaseOrderMapper.fromEntity(purchaseOrder);
    }

    @Transactional
    public void delete(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);

        if (purchaseOrder == null) { throw new RuntimeException("This purchaseorder does not exist"); }

        String status = purchaseOrder.getStatus();
        if ("closed".equals(status)) { throw new RuntimeException("Cannot delete closed order"); }

        purchaseOrder.getPurchasedItems().forEach((purchasedItem) -> {
            Long itemId = purchasedItem.getItemId();
            Integer quantity = purchasedItem.getQuantity();

            if ("confirmed".equals(status)) {
                itemRepository.decreaseArrivingQuantity(itemId, quantity);
            }

            purchasedItemRepository.delete(purchasedItem);
        });

        purchaseOrderRepository.deleteById(id);
    }

    @Transactional
    public Map<String, Object> confirm(Long id) {
        Map<String, Object> returnMap = new HashMap<>();

        PurchaseOrder purchaseOrder = findOneById(id);

        if (!purchaseOrder.getStatus().equals("draft")) {
            returnMap.put("code", 501);
            returnMap.put("msg", "Order's status must be draft");
            return returnMap;
        }

        purchaseOrder.getPurchasedItems().forEach((purchasedItem) -> {
            itemRepository.increaseArrivingQuantity(purchasedItem.getItemId(), purchasedItem.getQuantity());
        });

        purchaseOrder.setStatus("confirmed");
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);

        returnMap.put("code", 200);
        returnMap.put("data", purchaseOrderMapper.fromEntity(saved));
        return returnMap;
    }

    @Transactional
    public Map<String, Object> receive(Long id) {
        Map<String, Object> returnMap = new HashMap<>();

        PurchaseOrder purchaseOrder = findOneById(id);

        if (!purchaseOrder.getStatus().equals("confirmed")) {
            returnMap.put("code", 501);
            returnMap.put("msg", "Order's status must be confirmed");
            return returnMap;
        }

        purchaseOrder.getPurchasedItems().forEach((purchasedItem) -> {
            Long itemId = purchasedItem.getItemId();
            Integer quantity = purchasedItem.getQuantity();
            itemRepository.decreaseArrivingQuantity(itemId, quantity);
            itemRepository.increasePhysicalStock(itemId, quantity);
        });

        purchaseOrder.setStatus("closed");
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);

        returnMap.put("code", 200);
        returnMap.put("data", purchaseOrderMapper.fromEntity(saved));
        return returnMap;
    }

    public long getPurchaseOrderCount() {
        long PurchaseOrderCount = purchaseOrderRepository.count();
        return PurchaseOrderCount;
    }

    public long getTotalPrice() {
        long TotalPrice = 0;
        if (purchaseOrderRepository.count() != 0) {
            TotalPrice = purchaseOrderRepository.getTotalPurchaseOrderPrice();
        }
        return TotalPrice;
    }
}
