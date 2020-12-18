package com.hero.mappers;

import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.dtos.salesOrder.SalesOrderPutDto;
import com.hero.entities.Customer;
import com.hero.entities.SalesOrder;
import com.hero.repositories.CustomerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SalesOrderMapper {

    @Autowired
    private CustomerRepository customerRepository;

    @Mapping(source = "customer.name", target = "customer")
    public abstract SalesOrderGetDto fromEntity(SalesOrder salesOrder);

    @Mapping(source = "customer", target = "customer")
    public abstract SalesOrder toEntity(SalesOrderPostDto salesOrderPostDto);

    @Mapping(source = "customer", target = "customer")
    public abstract void copy(SalesOrderPutDto salesOrderPutDto, @MappingTarget SalesOrder salesOrder);

    public Customer nameToCustomer(String name) { return customerRepository.findByName(name); }

}
