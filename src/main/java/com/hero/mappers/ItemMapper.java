package com.hero.mappers;

import com.hero.dtos.brand.BrandPutDto;
import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.dtos.item.ItemPutDto;
import com.hero.entities.Brand;
import com.hero.entities.Item;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    @Mappings({
        @Mapping(source = "brand.name", target = "brand"),
        @Mapping(source = "manufacturer.name", target = "manufacturer")
    })
    ItemGetDto itemToItemGetDto(Item item);

    Item itemPostDtoToItem(ItemPostDto itemPostDto);
    void copy(ItemPutDto itemPutDto, @MappingTarget Item item);
}
