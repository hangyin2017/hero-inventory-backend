package com.hero.services;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.dtos.item.ItemPutDto;
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

    private Item findItem(Long id) {
        Item item = itemRepository.findById(id).orElse(null);

        if (item == null) { throw new RuntimeException("Item id=" + id + " does not exist."); }

        return item;
    }

    private List<ItemGetDto> itemsToItemGetDtos(List<Item> items) {
        return items.stream()
                .map(item -> itemMapper.itemToItemGetDto(item))
                .collect(Collectors.toList());
    }

    public List<ItemGetDto> getAllItems() {
        return itemsToItemGetDtos(itemRepository.findAll());
    }

    public ItemGetDto getOne(Long id) {
        return itemMapper.fromEntity(findItem(id));
    }

    public List<ItemGetDto> findByNameOrSkuLike(String searchInput) {
        List<Item> items = itemRepository.findByNameOrSkuLike("%" + searchInput.toLowerCase() + "%");

        return itemsToItemGetDtos(items);
    }

    public ItemGetDto addOne(ItemPostDto itemPostDto) {
        Item item = itemMapper.toEntity(itemPostDto);

        Item savedItem = itemRepository.save(item);

        return itemMapper.itemToItemGetDto(savedItem);
    }

    public ItemGetDto update(Long id, ItemPutDto itemPutDto){
        Item item = findItem(id);

        itemMapper.copy(itemPutDto, item);
        item.setId(id);

        return itemMapper.fromEntity(itemRepository.save(item));
    }

    public void delete(Long id){
        Item item = findItem(id);

        itemRepository.deleteById(id);
    }
}
