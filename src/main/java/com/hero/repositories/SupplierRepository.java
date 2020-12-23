package com.hero.repositories;

import com.hero.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SupplierRepository extends JpaRepository <Supplier, Long>{
    Supplier findByName (String name);

    @Query("select supplier from Supplier supplier where lower(supplier.name) like :searchInput")
    List<Supplier> findByNameLike(@RequestParam String searchInput);
}
