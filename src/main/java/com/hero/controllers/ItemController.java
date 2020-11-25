package com.hero.controllers;

import com.hero.dtos.item.ItemGetDto;
import com.hero.dtos.item.ItemPostDto;
import com.hero.dtos.item.ItemPutDto;
import com.hero.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemGetDto>> getAll() {
        List<ItemGetDto> itemGetDtoList = itemService.getAll();
        return ResponseEntity.ok(itemGetDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemGetDto> getOne(@PathVariable Long id) {
        ItemGetDto itemGetDto = itemService.getOne(id);
        return ResponseEntity.ok(itemGetDto);
    }

    @GetMapping("filter")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ItemGetDto>> filterItems(@RequestParam String searchInput) {
        return ResponseEntity.ok(itemService.findByNameOrSkuLike(searchInput));
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('item:write')")
    public ResponseEntity<ItemGetDto> addOne(@RequestBody ItemPostDto itemPostDto) {
        return ResponseEntity.ok(itemService.addOne(itemPostDto));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('item:write')")
    public ResponseEntity<ItemGetDto> update(@PathVariable Long id, @RequestBody ItemPutDto itemPutDto){
        return ResponseEntity.ok(itemService.update(id, itemPutDto));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity delete(@PathVariable Long itemId){
        itemService.delete(itemId);
        return ResponseEntity.ok().build();
    }
}
