package com.hero.repositories;

import com.hero.entities.Brand;
import com.hero.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
