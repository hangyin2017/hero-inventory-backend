package com.hero.repositories;

import com.hero.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    @Query(value = "select sum(total_price) from purchaseorders",nativeQuery = true)
    long getTotalPurchaseOrderPrice();
}
