package com.hero.mappers;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.brand.BrandPostDto;
import com.hero.dtos.brand.BrandPutDto;
import com.hero.entities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {

    Brand toEntity(BrandPostDto brandPostDto);

    BrandGetDto fromEntity(Brand brand);

    void copy(BrandPutDto brandPutDto, @MappingTarget Brand brand);
}
