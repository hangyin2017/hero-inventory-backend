package com.hero.services;

import com.hero.dtos.item.ItemGetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class ItemService {

    public List<ItemGetDto> getAllItems() {
        ItemGetDto itemGetDto = new ItemGetDto();
        itemGetDto.setName("Beauty Essential");
        itemGetDto.setBrand("Arrivite");
        itemGetDto.setQuantity(22);

        return List.of(itemGetDto);
    }
}
