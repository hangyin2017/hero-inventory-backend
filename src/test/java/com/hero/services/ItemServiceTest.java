package com.hero.services;

import com.hero.dtos.item.ItemGetDto;
import com.hero.entities.Item;
import com.hero.mappers.ItemMapper;
import com.hero.mappers.ItemMapperImpl;
import com.hero.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ItemMapperImpl.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    private ItemService itemService;

    @BeforeEach
    void setup() { itemService = new ItemService(itemRepository, itemMapper); }

    /*@Test
    public void shouldReturnItemGetDtoListGivenItemsExist() {
        Item item1 = new Item();
        item1.setCode("111");
        item1.setName("Beauty1");

        when(itemRepository.findAll()).thenReturn(List.of(item1));

        List<ItemGetDto> returnedItemGetDtoList = itemService.getAllItems();

        assertNotNull(returnedItemGetDtoList);
        assertEquals(1, returnedItemGetDtoList.size());
    }
*/
}
