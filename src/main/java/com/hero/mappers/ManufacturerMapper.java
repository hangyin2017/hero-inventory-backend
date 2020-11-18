package com.hero.mappers;


import com.hero.dtos.manufacturer.ManufacturerGetDto;
import com.hero.dtos.manufacturer.ManufacturerPostDto;
import com.hero.dtos.manufacturer.ManufacturerPutDto;
import com.hero.entities.Manufacturer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManufacturerMapper {

    ManufacturerGetDto fromEntity(Manufacturer manufacturer);

    Manufacturer toEntity(ManufacturerPostDto manufacturerPostDto);

    void copy(ManufacturerPutDto manufacturerPutDto, @MappingTarget Manufacturer manufacturer);
}
