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
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    public final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerGetDto>> getAll() {
        List<CustomerGetDto> customerGetDtoList = customerService.getAll();
        return ResponseEntity.ok(customerGetDtoList);
    }

    @PostMapping
    public ResponseEntity<CustomerGetDto> addOne(@RequestBody CustomerPostDto customerPostDto) {
        return ResponseEntity.ok(customerService.addOne(customerPostDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerGetDto> update(@PathVariable Long id, @RequestBody CustomerPutDto customerPutDto) {
        CustomerGetDto customerGetDto = customerService.update(id, customerPutDto);
        return ResponseEntity.ok(customerGetDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
