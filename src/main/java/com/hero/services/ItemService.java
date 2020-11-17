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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private List<ItemGetDto> itemsToItemGetDtos(List<Item> items) {
        return items.stream()
                .map(item -> itemMapper.itemToItemGetDto(item))
                .collect(Collectors.toList());
    }

    public List<ItemGetDto> getAllItems() {
        return itemsToItemGetDtos(itemRepository.findAll());
    }

    public List<ItemGetDto> findByNameOrSkuLike(String searchInput) {
        List<Item> items = itemRepository.findByNameOrSkuLike("%" + searchInput.toLowerCase() + "%");

        return itemsToItemGetDtos(items);
    }

    public ItemGetDto postItem(ItemPostDto itemPostDto) {
        Item item = itemMapper.itemPostDtoToItem(itemPostDto);

        Item savedItem = itemRepository.save(item);

        return itemMapper.itemToItemGetDto(savedItem);
    }


}
