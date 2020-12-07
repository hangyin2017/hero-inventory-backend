/*package com.hero.services;

import com.hero.dtos.item.ItemGetDto;
import com.hero.entities.Item;
import com.hero.mappers.ItemMapper;
import com.hero.mappers.ItemMapperImpl;
import com.hero.repositories.BrandRepository;
import com.hero.repositories.ItemRepository;
import com.hero.utils.Utility;
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

    @Mock
    private BrandRepository brandRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private Utility utility;

    private ItemService itemService;

    @BeforeEach
    void setup() { itemService = new ItemService(itemRepository, itemMapper); }

    @Test
    public void shouldReturnItemGetDtoListGivenItemsExist() {
        // Given
        Item item1 = new Item();
        item1.setSku("111");
        item1.setName("Health1");
        Item item2 = utility.buildItem((long) 2,
                "sku2",
                "Health2",
                true);

        // When
        when(itemRepository.findAll()).thenReturn(List.of(item1, item2));
        List<ItemGetDto> returnedItemGetDtoList = itemService.getAll();

        // Then
        assertNotNull(returnedItemGetDtoList);
        assertEquals(2, returnedItemGetDtoList.size());
        assertEquals("Health2", returnedItemGetDtoList.get(1).getName());
    }
}*/
