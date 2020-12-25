package com.hero.mappers;

import com.hero.dtos.purchaseOrder.PurchaseOrderGetDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPostDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPutDto;
import com.hero.entities.PurchaseOrder;
import com.hero.entities.Supplier;
import com.hero.repositories.SupplierRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PurchaseOrderMapper {

    @Autowired
    private SupplierRepository supplierRepository;

    @Mapping(source = "supplier.name", target = "supplier")
    public abstract PurchaseOrderGetDto fromEntity(PurchaseOrder purchaseOrder);

    @Mapping(source = "supplier", target = "supplier")
    public abstract PurchaseOrder toEntity(PurchaseOrderPostDto purchaseOrderPostDto);

    @Mapping(source = "supplier", target = "supplier")
    public abstract void copy(PurchaseOrderPutDto purchaseOrderPutDto, @MappingTarget PurchaseOrder purchaseOrder);

    public Supplier nameToSupplier(String name) { return supplierRepository.findByName(name); }
}
