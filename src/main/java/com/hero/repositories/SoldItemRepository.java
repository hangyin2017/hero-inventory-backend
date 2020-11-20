package com.hero.repositories;

import com.hero.entities.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldItemRepository extends JpaRepository<SoldItem, Long> {
}
