package com.hero.repositories;

import com.hero.entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    @Query("select salesOrder from SalesOrder salesOrder where lower(salesOrder.salesorderNumber) like :searchInput")
    List<SalesOrder> findByNumberLike(@RequestParam String searchInput);

    @Query(value = "select sum(total_price) from salesorders",nativeQuery = true)
    long getTotalSalesOrderPrice();
}
