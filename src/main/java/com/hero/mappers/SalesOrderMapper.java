package com.hero.mappers;

import com.hero.dtos.salesOrder.SalesOrderGetDto;
import com.hero.dtos.salesOrder.SalesOrderPostDto;
import com.hero.dtos.salesOrder.SalesOrderPutDto;
import com.hero.entities.SalesOrder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalesOrderMapper {

    SalesOrder toEntity(SalesOrderPostDto salesOrderPostDto);

    SalesOrderGetDto fromEntity(SalesOrder salesOrder);

    void copy(SalesOrderPutDto salesOrderPutDto, @MappingTarget SalesOrder salesOrder);
}
