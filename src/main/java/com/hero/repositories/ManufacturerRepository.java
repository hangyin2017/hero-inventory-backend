package com.hero.repositories;

import com.hero.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Manufacturer findByName(String name);

    List<Manufacturer> findByNameLike(@RequestParam String name);
}
