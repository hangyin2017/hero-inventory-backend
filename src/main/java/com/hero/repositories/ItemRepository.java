package com.hero.repositories;

import com.hero.entities.Brand;
import com.hero.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByNameLike(@RequestParam String name);

    List<Item> findBySkuLike(@RequestParam String sku);

    @Query("select item from Item item where lower(item.name) like :searchInput or lower(item.sku) like :searchInput")
    List<Item> findByNameOrSkuLike(@RequestParam String searchInput);

    List<Item> findByBrand(Brand brand);
}

