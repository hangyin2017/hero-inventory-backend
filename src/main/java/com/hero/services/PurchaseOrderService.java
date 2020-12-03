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

    private List<PurchaseOrderGetDto> fromEntity(List<PurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream()
                .map(purchaseOrder -> purchaseOrderMapper.fromEntity(purchaseOrder))
                .collect(Collectors.toList());
    }

    public List<PurchaseOrderGetDto> getAllPurchaseOrders(){
        return fromEntity(purchaseOrderRepository.findAll());
    };

    @Transactional
    public Map<String, Object> addPurchaseOrder(PurchaseOrderPostDto purchaseOrderPostDto) {

        Map<String, Object> returnMap = new HashMap<>();

        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(purchaseOrderPostDto);

        List<Item> itemIdList = new ArrayList<Item>();
        for (PurchasedItem purchasedItem : purchaseOrder.getPurchasedItems()) {

            Long id = purchasedItem.getItemId();
            Long quantity = purchasedItem.getQuantity();

            Item item = itemRepository.findById(id).orElse(null);
            if (quantity > 0) {
                itemIdList.add(item);
            }
        }

        if (itemIdList.size() > 0) {
            PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);

            for (PurchasedItem purchasedItem : purchaseOrder.getPurchasedItems()) {
                purchasedItem.setPurchaseOrder(savedOrder);
                purchasedItemRepository.save(purchasedItem);
                itemRepository.increaseItemStock(purchasedItem.getItemId(), purchasedItem.getQuantity());
            }
            returnMap.put("code", 200);
            returnMap.put("data", purchaseOrderMapper.fromEntity(savedOrder));
        } else {
            StringBuffer msg = new StringBuffer();

            msg.append("You can not create a purchase order without an item, ");
            msg.append("please add an item!");
            returnMap.put("code", 501);
            returnMap.put("msg", msg);
        }

        return returnMap;

    }

    @Transactional
    public PurchaseOrderGetDto modifyPurchaseOrder(Long purchaseorderId, PurchaseOrderPutDto purchaseOrderPutDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseorderId).orElse(null);
        purchasedItemRepository.deleteByPurchaseOrder(purchaseOrder);
        if (purchaseOrder == null) {
            throw new RuntimeException("This purchaseorder is not exist.");
        }
        purchaseOrderMapper.copy(purchaseOrderPutDto, purchaseOrder);
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);
        Set<PurchasedItem> purchasedItems = purchaseOrder.getPurchasedItems().stream().peek(e -> e.setPurchaseOrder(saved)).collect(Collectors.toSet());
        purchasedItemRepository.saveAll(purchasedItems);
        purchaseOrder.setPurchasedItems(purchasedItems);
        return purchaseOrderMapper.fromEntity(purchaseOrder);
    }

    @Transactional
    public void deletePurchaseOrder(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElse(null);
        if (purchaseOrder == null) {
            throw new RuntimeException("This purchaseorder is not exist.");
        } else {
            purchaseOrderRepository.deleteById(purchaseOrderId);
            for (PurchasedItem purchasedItem : purchaseOrder.getPurchasedItems()) {
                purchasedItemRepository.delete(purchasedItem);
            }
        }
    }



}
