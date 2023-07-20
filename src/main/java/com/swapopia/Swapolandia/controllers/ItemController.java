package com.swapopia.Swapolandia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swapopia.Swapolandia.model.Item;
import com.swapopia.Swapolandia.repository.ItemRepository;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    // Endpoint para buscar todos os itens
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Outros endpoints para manipulação de itens (criação, atualização e exclusão)

}
