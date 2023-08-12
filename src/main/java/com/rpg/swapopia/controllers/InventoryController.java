package com.rpg.swapopia.controllers;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpg.swapopia.model.item.Box;
import com.rpg.swapopia.model.item.Inventory;
import com.rpg.swapopia.model.item.InventoryContentsResponse;
import com.rpg.swapopia.model.item.Item;
import com.rpg.swapopia.model.item.dto.BoxDTO;
import com.rpg.swapopia.model.user.User;
import com.rpg.swapopia.repositories.BoxRepository;
import com.rpg.swapopia.repositories.InventoryRepository;
//import com.rpg.swapopia.repositories.BoxRepository;
//import com.rpg.swapopia.repositories.UserRepository;
import com.rpg.swapopia.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    // endpoint para retornar a lista de itens e caixas do inventario
    @GetMapping
    public ResponseEntity<InventoryContentsResponse> getItemsAndBoxesInInventory(
            @AuthenticationPrincipal User authenticatedUser) {
        // Find the user's inventory based on the authenticated user
        Inventory userInventory = inventoryRepository.findByUser(authenticatedUser);

        if (userInventory == null) {
            return ResponseEntity.notFound().build();
        }

        InventoryContentsResponse response = new InventoryContentsResponse();
        response.setItems(userInventory.getItems());
        response.setBoxes(userInventory.getBoxes());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/boxes")
    public ResponseEntity<List<BoxDTO>> getBoxesByUserId(@PathVariable Long userId) {
        List<BoxDTO> boxes = inventoryService.getBoxesByUserId(userId);
        return ResponseEntity.ok(boxes);
    }

    @PostMapping("/abrir/{boxType}")
    public ResponseEntity<String> openBoxByType(
            @PathVariable String boxType,
            @AuthenticationPrincipal User authenticatedUser) {
        Inventory userInventory = inventoryRepository.findByUser(authenticatedUser);
        List<Box> boxesOfType = boxRepository.findAllByType(boxType);

        if (userInventory == null || boxesOfType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Box boxToOpen = boxesOfType.get(0); // Assuming we want to open the first box of the specified type

        List<Item> generatedItems = generateItems(boxType, boxToOpen);

        userInventory.getItems().addAll(generatedItems);
        userInventory.getBoxes().remove(boxToOpen); // Remove the opened box from inventory

        inventoryRepository.save(userInventory);
        boxRepository.delete(boxToOpen); // Delete the opened box from the database

        return ResponseEntity.ok("Caixa aberta com Sucesso");
    }

    private List<Item> generateItems(String boxType, Box box) {
        Map<String, float[]> itemTypeValueRanges = new HashMap<>();
        itemTypeValueRanges.put("Material", new float[]{10, 50});
        itemTypeValueRanges.put("Espada", new float[]{100, 350});
        itemTypeValueRanges.put("Arco", new float[]{90, 400});
        itemTypeValueRanges.put("Roupa", new float[]{50, 140});

        int itemsCount = (boxType.equals("Material")) ? new Random().nextInt(5) + 1 : 1;

        return new Random().ints(itemsCount, 0, 3)
            .mapToObj(index -> generateItem(boxType, box, itemTypeValueRanges, index))
            .collect(Collectors.toList());
    }

    private Item generateItem(String boxType, Box box, Map<String, float[]> itemTypeValueRanges, int index) {
        String itemType = (boxType.equals("Material")) ? boxType : getRandomItemType(boxType);
        String itemName = getRandomItemName(itemType);
        float[] valueRange = itemTypeValueRanges.get(itemType);
        float itemValue = getRandomValueInRange(valueRange[0], valueRange[1]);

        Item newItem = new Item();
        newItem.setName(itemName);
        newItem.setPrice(itemValue);
        newItem.setInventory(box.getInventory());

        return newItem;
    }

    private float getRandomValueInRange(float min, float max) {
        return min + new Random().nextFloat() * (max - min);
    }

    private String getRandomItemType(String boxType) {
        String[] itemTypes = { "Espada", "Arco", "Roupa" };
        return itemTypes[new Random().nextInt(itemTypes.length)];
    }

    private String getRandomItemName(String itemType) {
        Map<String, List<String>> itemNamesMap = new HashMap<>();
        itemNamesMap.put("Espada", Arrays.asList("Katana", "Adaga"));
        itemNamesMap.put("Arco", Arrays.asList("Arco curto", "Arco longo"));
        itemNamesMap.put("Roupa", Arrays.asList("Roupa de feiticeiro", "Roupa de ladrão", "Roupa de guerreiro"));
        itemNamesMap.put("Material", Arrays.asList("Pedra", "Madeira", "Argila"));

        return itemNamesMap.get(itemType).get(new Random().nextInt(itemNamesMap.get(itemType).size()));
    }
}


