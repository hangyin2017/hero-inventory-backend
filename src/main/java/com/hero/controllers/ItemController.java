package com.hero.controllers;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemGetDto>> getAllItems() {
        List<ItemGetDto> itemGetDtoList = itemService.getAllItems();
        return ResponseEntity.ok(itemGetDtoList);
    }

    @GetMapping("search")
    public ResponseEntity<List<ItemGetDto>> findByNameLike(@RequestParam String searchInput) {
        return ResponseEntity.ok(itemService.findByNameOrCodeLike(searchInput));
    }

    @PostMapping
    public ResponseEntity<ItemGetDto> postItems(@RequestBody ItemPostDto itemPostDto) {
        return ResponseEntity.ok(itemService.postItem(itemPostDto));
    }
}
