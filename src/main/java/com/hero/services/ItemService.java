package com.hero.services;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.entities.Item;
import com.hero.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemGetDto> getAllItems() {
        List<ItemGetDto> itemGetDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findAll();

        itemList.forEach(item -> {
            ItemGetDto itemGetDto = new ItemGetDto();
            itemGetDto.setName(item.getName());
            itemGetDto.setCode(item.getCode());
            itemGetDtoList.add(itemGetDto);
        });

        return itemGetDtoList;
    }

    //public ItemGetDto getByCode(String code) {
    //
    //}

    public ItemGetDto postItem(ItemPostDto itemPostDto) {
        Item item = new Item();
        item.setCode(itemPostDto.getCode());
        item.setName(itemPostDto.getName());

        Item responseItem = itemRepository.save(item);

        ItemGetDto itemGetDto = new ItemGetDto();
        itemGetDto.setId(responseItem.getId());
        itemGetDto.setCode(responseItem.getCode());
        itemGetDto.setName(responseItem.getName());

        return itemGetDto;
    }
}
