package com.hero.mappers;

import com.hero.dtos.supplier.SupplierGetDto;
import com.hero.dtos.supplier.SupplierPostDto;
import com.hero.dtos.supplier.SupplierPutDto;
import com.hero.entities.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierMapper {
    Supplier toEntity(SupplierPostDto customerPostDto);

    SupplierGetDto fromEntity(Supplier customer);

    void copy(SupplierPutDto customerPutDto, @MappingTarget Supplier customer);
}
