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
    Supplier toEntity(SupplierPostDto supplierPostDto);

    SupplierGetDto fromEntity(Supplier supplier);

    void copy(SupplierPutDto supplierPutDto, @MappingTarget Supplier supplier);
}
