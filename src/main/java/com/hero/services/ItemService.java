package com.hero.services;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.entities.Item;
import com.hero.mappers.ItemMapper;
import com.hero.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<ItemGetDto> getAllItems() {
        List<ItemGetDto> itemGetDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findAll();

        itemList.forEach(item -> {
            ItemGetDto itemGetDto = itemMapper.itemToItemGetDto(item);
            itemGetDtoList.add(itemGetDto);
        });

        return itemGetDtoList;
    }

    public List<ItemGetDto> findByNameOrCodeLike(String searchInput) {
        List<ItemGetDto> itemGetDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findByNameOrCodeLike("%" + searchInput.toLowerCase() + "%");

        itemList.forEach(item -> {
            ItemGetDto itemGetDto = itemMapper.itemToItemGetDto(item);
            itemGetDtoList.add(itemGetDto);
        });

        return itemGetDtoList;
    }

    public ItemGetDto postItem(ItemPostDto itemPostDto) {
        Item item = itemMapper.itemPostDtoToItem(itemPostDto);

        Item savedItem = itemRepository.save(item);

        return itemMapper.itemToItemGetDto(savedItem);
    }
}
