package com.hero.repositories;

import com.hero.DemoApplication;
import com.hero.entities.Brand;
import com.hero.entities.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = DemoApplication.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class ItemRepositoryTest {
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Test
//    public void shouldInsertIntoDBGivenCorrectItemObject() {
//        // Given
//        Item item = new Item();
//        item.setName("beauty1");
//        item.setCode("111");
//
//        // When
//        Item itemInDB = itemRepository.save(item);
//
//        // Then
//        Assertions.assertEquals("beauty1", itemInDB.getName());
//        Assertions.assertEquals("111", itemInDB.getCode());
//    }

//    @Test
//    public void test1(){
//        Brand brand = new Brand();
//        brand.setName("A2");
//        brand.setId(Long.parseLong("1"));
//        List<Item> list= itemRepository.findByBrand(brand);
//        System.out.println(list.size());
//    }
//}
