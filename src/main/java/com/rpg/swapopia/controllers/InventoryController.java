package com.rpg.swapopia.controllers;

import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpg.swapopia.domain.item.Item;
import com.rpg.swapopia.domain.item.dto.BoxDTO;
import com.rpg.swapopia.domain.user.User;
//import com.rpg.swapopia.repositories.BoxRepository;
//import com.rpg.swapopia.repositories.UserRepository;
import com.rpg.swapopia.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    /*/@Autowired
    private BoxRepository boxRepository; // Supondo que você tenha um repositório para caixas
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;
    @Autowired*/
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //endpoint para retornar a lista de itens e caixas do inventario
    @GetMapping
    public ResponseEntity<List<Item>> getInventory(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User authenticatedUser = (User) authentication.getPrincipal();
        List<Item> userInventory = inventoryService.getInventoryByUser(authenticatedUser);

        return ResponseEntity.ok(userInventory);
    }

    @GetMapping("/boxes")
    public ResponseEntity<List<BoxDTO>> getBoxesByUserId(@PathVariable Long userId) {
        List<BoxDTO> boxes = inventoryService.getBoxesByUserId(userId);
        return ResponseEntity.ok(boxes);
    }
   
}
