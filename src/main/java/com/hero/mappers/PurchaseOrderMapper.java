package com.hero.mappers;

import com.hero.dtos.purchaseOrder.PurchaseOrderGetDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPostDto;
import com.hero.dtos.purchaseOrder.PurchaseOrderPutDto;
import com.hero.entities.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseOrderMapper {

    PurchaseOrder toEntity(PurchaseOrderPostDto purchaseOrderPostDto);

    PurchaseOrderGetDto fromEntity(PurchaseOrder purchaseOrder);

    void copy(PurchaseOrderPutDto purchaseOrderPutDto, @MappingTarget PurchaseOrder purchaseOrder);
}
