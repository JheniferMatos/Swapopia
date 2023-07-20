package com.swapopia.Swapolandia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swapopia.Swapolandia.model.InventoryItem;
import com.swapopia.Swapolandia.repository.InventoryItemRepository;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryItemController {


    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    // Endpoint para buscar todos os itens de inventário
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    // Outros endpoints para manipulação de itens de inventário (criação, atualização e exclusão)

}
