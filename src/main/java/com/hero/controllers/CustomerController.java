package com.hero.controllers;

import com.hero.dtos.customer.CustomerGetDto;
import com.hero.dtos.customer.CustomerPostDto;
import com.hero.dtos.customer.CustomerPutDto;
import com.hero.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    public final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerGetDto>> getAllCustomers() {
        List<CustomerGetDto> customerGetDtoList = customerService.getAllContacts();
        return ResponseEntity.ok(customerGetDtoList);
    }

    @PostMapping
    public ResponseEntity<CustomerGetDto> postCustomers(@RequestBody CustomerPostDto customerPostDto) {
        return ResponseEntity.ok(customerService.postContact(customerPostDto));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerGetDto> update(@PathVariable Long customerId, @RequestBody CustomerPutDto customerPutDto) {
        CustomerGetDto customerGetDto = customerService.modify(customerId, customerPutDto);
        return ResponseEntity.ok(customerGetDto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteCustomers(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.ok().build();
    }
}
