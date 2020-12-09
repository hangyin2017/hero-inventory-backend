package com.hero.utils;

import com.hero.entities.Item;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utility {

    public Item buildItem(Long id,
                          String sku,
                          String name,
                          Boolean active) {
        Item item = new Item();
        item.setId(id);
        item.setSku(sku);
        item.setName(name);
        item.setActive(active);

        return item;
    }
}
