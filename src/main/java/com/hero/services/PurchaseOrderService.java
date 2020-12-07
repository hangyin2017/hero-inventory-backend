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

        if (!purchaseOrderPostDto.getStatus().equals("draft")) {
            returnMap.put("code", 501);
            returnMap.put("msg", "Order's status must be draft");
            return returnMap;
        }

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
    public PurchaseOrderGetDto update(Long id, PurchaseOrderPutDto purchaseOrderPutDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        purchasedItemRepository.deleteByPurchaseOrder(purchaseOrder);
        if (purchaseOrder == null) {
            throw new RuntimeException("This purchaseorder does not exist.");
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
        if (purchaseOrder == null) {
            throw new RuntimeException("This purchaseorder does not exist.");
        } else {
            purchaseOrderRepository.deleteById(id);
            for (PurchasedItem purchasedItem : purchaseOrder.getPurchasedItems()) {
                purchasedItemRepository.delete(purchasedItem);
            }
        }
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

        purchaseOrder.setStatus("confirmed");
        returnMap.put("code", 200);
        returnMap.put("data", purchaseOrderMapper.fromEntity(purchaseOrder));
        return returnMap;
    }
}
