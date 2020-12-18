package com.hero.mappers;

import com.hero.dtos.customer.CustomerGetDto;
import com.hero.dtos.customer.CustomerPostDto;
import com.hero.dtos.customer.CustomerPutDto;
import com.hero.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer toEntity(CustomerPostDto customerPostDto);

    CustomerGetDto fromEntity(Customer customer);

    void copy(CustomerPutDto customerPutDto, @MappingTarget Customer customer);
}
