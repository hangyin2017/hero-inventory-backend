package com.hero.repositories;

import com.hero.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findByNameLike(@RequestParam String name);

    @Modifying(flushAutomatically = true)
    @Query("update Manufacturer m set m.name = :name")
    int updateManufacturerName(@Param("name") String name);
}