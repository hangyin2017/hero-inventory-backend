package com.hero.mappers;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.dtos.item.ItemPutDto;
import com.hero.entities.Brand;
import com.hero.entities.Item;
import com.hero.entities.Manufacturer;
import com.hero.repositories.BrandRepository;
import com.hero.repositories.ManufacturerRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ItemMapper {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Mapping(source = "brand.name", target = "brand")
    @Mapping(source = "manufacturer.name", target = "manufacturer")
    public abstract ItemGetDto fromEntity(Item item);

    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "manufacturer", target = "manufacturer")
    public abstract Item toEntity(ItemPostDto itemPostDto);

    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "manufacturer", target = "manufacturer")
    public abstract void copy(ItemPutDto itemPutDto, @MappingTarget Item item);

    public Manufacturer nameToManufacturer(String name) { return manufacturerRepository.findByName(name); }

    public Brand nameToBrand(String name) {
        return brandRepository.findByName(name);
    }
}
