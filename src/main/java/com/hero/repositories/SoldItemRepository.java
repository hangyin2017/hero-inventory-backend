package com.hero.repositories;

import com.hero.entities.SalesOrder;
import com.hero.entities.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoldItemRepository extends JpaRepository<SoldItem, Long> {

    void deleteBySalesOrder(SalesOrder salesOrder);
}

