package com.rpg.swapopia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpg.swapopia.model.item.Item;
import com.rpg.swapopia.repositories.ItemRepository;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAllByOrderByNameAsc();
    }

    // Endpoint para criar novo item
    /*
     * @PostMapping
     * public ResponseEntity<Void> NewItem(@RequestBody @Valid ItemRequestDTO body)
     * {
     * Item newItem = new Item(body);
     * 
     * this.repository.save(newItem);
     * return ResponseEntity.ok().build();
     * }
     */

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteItem(@PathVariable String name) {
        itemRepository.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
