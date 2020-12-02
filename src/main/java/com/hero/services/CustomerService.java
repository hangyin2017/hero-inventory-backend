package com.hero.services;

import com.hero.dtos.customer.CustomerGetDto;
import com.hero.dtos.customer.CustomerPostDto;
import com.hero.dtos.customer.CustomerPutDto;
import com.hero.entities.Customer;
import com.hero.mappers.CustomerMapper;
import com.hero.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private List<CustomerGetDto> fromEntity(List<Customer> customers) {
        return customers.stream()
                .map(customer -> customerMapper.fromEntity(customer))
                .collect(Collectors.toList());
    }

    public List<CustomerGetDto> getAllContacts() {
        return fromEntity(customerRepository.findAll());
    }

    public List<CustomerGetDto> findByName(String name) {
        List<Customer> customers = customerRepository.findByNameLike("%" + name.toLowerCase() + "%");

        return fromEntity(customers);
    }

    public CustomerGetDto postContact(CustomerPostDto customerPostDto) {
        Customer customer = customerMapper.toEntity(customerPostDto);
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.fromEntity(savedCustomer);
    }

    public CustomerGetDto modify(Long customerId, CustomerPutDto customerPutDto) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new RuntimeException("This customer is not exist.");
        }
        customerMapper.copy(customerPutDto, customer);

        customer.setId(customerId);

        return customerMapper.fromEntity(customerRepository.save(customer));
    }

    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer.getSalesOrders() == null || customer.getSalesOrders().isEmpty()) {
            customerRepository.deleteById(customerId);
        } else {
            throw new RuntimeException("Can not delete customer with related items.");
        }
    }
}
