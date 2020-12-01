package com.hero.repositories;

import com.hero.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
    List<Customer> findByNameLike(@RequestParam String customerName);
}
