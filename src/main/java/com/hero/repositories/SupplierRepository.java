package com.hero.repositories;

import com.hero.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SupplierRepository extends JpaRepository <Supplier, Long>{
    Supplier findByName (String name);

    List<Supplier> findByNameLike(@RequestParam String name);
}
