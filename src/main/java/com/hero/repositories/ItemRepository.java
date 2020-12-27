package com.hero.repositories;

import com.hero.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select item from Item item where lower(item.name) like :searchInput or lower(item.sku) like :searchInput")
    List<Item> findByNameOrSkuLike(@RequestParam String searchInput);

    @Query(value = "select count(*) from items where physical_stock < 10",nativeQuery = true)
    long getLowStockItem();

	@Modifying
	@Query("update Item" +
            " set physical_stock=physical_stock- :quantity" +
            " where item_id= :id")
	int decreasePhysicalStock(Long id, Integer quantity);

	@Modifying
    @Query("update Item" +
            " set physical_stock=physical_stock+ :quantity" +
            " where item_id= :id")
    int increasePhysicalStock(Long id, Integer quantity);

    @Modifying
    @Query("update Item" +
            " set locked_stock=locked_stock- :quantity" +
            " where item_id= :id")
    int decreaseLockedStock(Long id, Integer quantity);

    @Modifying
    @Query("update Item" +
            " set locked_stock=locked_stock+ :quantity" +
            " where item_id= :id")
    int increaseLockedStock(Long id, Integer quantity);

    @Modifying
    @Query("update Item" +
            " set arriving_quantity=arriving_quantity- :quantity" +
            " where item_id= :id")
    int decreaseArrivingQuantity(Long id, Integer quantity);

    @Modifying
    @Query("update Item" +
            " set arriving_quantity=arriving_quantity+ :quantity" +
            " where item_id= :id")
    int increaseArrivingQuantity(Long id, Integer quantity);
}

