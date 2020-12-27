package com.hero.repositories;

import com.hero.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("select purchaseOrder from PurchaseOrder purchaseOrder where lower(purchaseOrder.purchaseorderNumber) like :searchInput")
    List<PurchaseOrder> findByNumberLike(@RequestParam String searchInput);

    @Query(value = "select COALESCE(sum(total_price),0) from purchaseorders",nativeQuery = true)
    long getTotalPurchaseOrderPrice();
}
