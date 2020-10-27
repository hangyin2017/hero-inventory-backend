package com.hero.repositories;

import com.hero.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface ItemRepository extends JpaRepository<Item, Id> {
}
