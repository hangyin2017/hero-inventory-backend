package com.hero.repositories;

import com.hero.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SupplierRepository extends JpaRepository <Supplier, Long>{
    List<Supplier> findBySupplierNameLike(@RequestParam String supplierName);
}
