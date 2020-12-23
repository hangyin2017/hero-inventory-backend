package com.hero.repositories;

import com.hero.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
    Customer findByName (String name);

    @Query("select customer from Customer customer where lower(customer.name) like :searchInput")
    List<Customer> findByNameLike(@RequestParam String searchInput);

}
