package com.hero.repositories;

import com.hero.entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    @Query(value = "select sum(total_price) from salesorders",nativeQuery = true)
    long getTotalSalesOrderPrice();
}
