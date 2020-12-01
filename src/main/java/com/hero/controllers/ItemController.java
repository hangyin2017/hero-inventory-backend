package com.hero.controllers;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemGetDto>> getAllItems() {
        List<ItemGetDto> itemGetDtoList = itemService.getAllItems();
        return ResponseEntity.ok(itemGetDtoList);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemGetDto> findById(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findItemById(itemId));
    }

    @GetMapping("filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ItemGetDto>> filterItems(@RequestParam String searchInput) {
        return ResponseEntity.ok(itemService.findByNameOrSkuLike(searchInput));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('item:write')")
    public ResponseEntity<ItemGetDto> postItems(@RequestBody ItemPostDto itemPostDto) {
        return ResponseEntity.ok(itemService.postItem(itemPostDto));
    }


}
