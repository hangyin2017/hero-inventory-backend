package com.hero.repositories;

import com.hero.entities.PurchaseOrder;
import com.hero.entities.PurchasedItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedItemRepository extends JpaRepository<PurchasedItem, Long> {

    void deleteByPurchaseOrder(PurchaseOrder purchaseOrder);
}

