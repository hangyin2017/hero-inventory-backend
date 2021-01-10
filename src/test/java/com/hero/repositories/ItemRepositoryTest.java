package com.hero.repositories;

import com.hero.InventoryApplication;
import com.hero.entities.Item;
import com.hero.utils.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InventoryApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private  Utility utility;

    //@BeforeEach
    //private void mockItems() {
    //    Item item1 = utility.buildItem(1L, "health001", "health1", true);
    //    Item item2 = utility.buildItem(2L, "health002", "health2", true);
    //}

    @Test
    public void shouldReturnItemListGivenItemsHaveBeenInserted() {
        // Given
        Item item1 = utility.buildItem(1L, "health001", "health1", true);
        Item item2 = utility.buildItem(2L, "health002", "health2", true);

        // When
        itemRepository.save(item1);
        itemRepository.save(item2);

        // Then
        List<Item> itemList = itemRepository.findAll();
        assertEquals(2, itemList.size());
        assertEquals(item1.getSku(), itemList.get(0).getSku());
    }

    @Test
    public void shouldReturnItemGivenItemId(){
        // Given
        Item item1 = utility.buildItem(1L, "health001", "health1", true);
        Item item2 = utility.buildItem(2L, "health002", "health2", true);

        // When
        itemRepository.save(item1);
        itemRepository.save(item2);

        // Then
        Item returnedItem1 = itemRepository.findById(1L).orElse(null);
        assertEquals(item1.getName(), returnedItem1.getName());
        Item nullItem = itemRepository.findById(3L).orElse(null);
        assertNull(nullItem);
    }
}
