package com.hero.mappers;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    @Mappings({
        @Mapping(source = "brand.name", target = "brand"),
        @Mapping(source = "manufacturer.name", target = "manufacturer")
    })
    ItemGetDto itemToItemGetDto(Item item);

    Item itemPostDtoToItem(ItemPostDto itemPostDto);
}
