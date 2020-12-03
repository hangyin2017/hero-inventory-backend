package com.hero.repositories;

import com.hero.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByNameLike(@RequestParam String name);

    List<Item> findBySkuLike(@RequestParam String sku);

    List<Item> findByIdLike(@RequestParam Long id);
	
	List<Item> findByIdIn(@RequestParam List<String> id);

    @Query("select item from Item item where lower(item.name) like :searchInput or lower(item.sku) like :searchInput")
    List<Item> findByNameOrSkuLike(@RequestParam String searchInput);
	
	@Modifying
	@Query("update Item set physical_stock=physical_stock- :quantity where item_id= :id")
	int decreaseItemStock(Long id, Long quantity);

	@Modifying
    @Query("update Item set physical_stock=physical_stock+ :quantity where item_id= :id")
    int increaseItemStock(Long id, Long quantity);
}

