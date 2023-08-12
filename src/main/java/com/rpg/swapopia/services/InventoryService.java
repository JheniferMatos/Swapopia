package com.rpg.swapopia.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.rpg.swapopia.domain.item.Box;
import com.rpg.swapopia.domain.item.Inventory;
import com.rpg.swapopia.domain.item.Item;
import com.rpg.swapopia.domain.item.dto.BoxDTO;
import com.rpg.swapopia.domain.user.User;
import com.rpg.swapopia.repositories.InventoryRepository;
import com.rpg.swapopia.repositories.UserRepository;

@Service
public class InventoryService {
    // @Autowired
    // private ItemRepository itemRepository; // Supondo que você tenha um
    // repositório para itens
    
    private final InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    
    public List<Item> getInventoryByUser(User user) {
        // Supondo que Inventory tenha uma lista de itens chamada "items"
        Inventory userInventory = inventoryRepository.findByUser(user);
        return userInventory.getItems();
    }

    public void purchaseBox(User user, Box box) throws Exception {
        if (user.getCash() >= box.getPrice()) {
            user.setCash((float) user.getCash() - (float) box.getPrice());

            Inventory inventory = user.getInventory();
            inventory.getBoxes().add(box);
            userRepository.save(user);
        } else {
            throw new Exception("Insufficient funds to purchase the box");
        }
    }
    public void openBox(Inventory inventory, Box box) {
        // Aqui você implementa a lógica para gerar itens aleatórios com base no tipo da
        // caixa
        List<Item> itemsReceived = generateRandomItemsFromBox(box);

        inventory.getItems().addAll(itemsReceived);
        inventory.getBoxes().remove(box);
        // Atualiza o inventário no banco de dados
        inventoryRepository.save(inventory);
    }

    private List<Item> generateRandomItemsFromBox(Box box) {
        List<Item> items = new ArrayList<>();
        Random random = new Random();
    
        int numberOfItems = 1; // Por padrão, uma caixa gera apenas 1 item
    
        if (box.getType().equals("Material")) {
            numberOfItems = random.nextInt(5) + 1; // Gera um número aleatório entre 1 e 5
        } else if (box.getType().equals("Espada")) {
        } else if (box.getType().equals("Arco")) {
        } else if (box.getType().equals("Roupa")) {
        }
    
        for (int i = 0; i < numberOfItems; i++) {
            Item item = new Item();
            item.setName(generateRandomItemName(box.getType())); // Defina esse método para gerar nomes aleatórios com base no tipo de caixa
            items.add(item);
        }
    
        return items;
    }
    
    private String generateRandomItemName(String boxType) {
        if (boxType.equals("Material")) {
            String[] materialNames = {"Pedra", "Madeira", "Argila"};
            return materialNames[new Random().nextInt(materialNames.length)];
        } else if (boxType.equals("Espada")) {
            String[] espadaNames = {"Katana", "Adaga"};
            return espadaNames[new Random().nextInt(espadaNames.length)];
        } else if (boxType.equals("Arco")) {
            String[] arcoNames = {"Arco longo", "Arco curto"};
            return arcoNames[new Random().nextInt(arcoNames.length)];
        } else if (boxType.equals("Roupa")) {
            String[] roupaNames = {"Roupa de feiticeiro", "Roupa de ladrão", "Roupa de guerreiro"};
            return roupaNames[new Random().nextInt(roupaNames.length)];
        }
    
        return "Item";
    }
    

    //Simulando um repositório ou alguma fonte de dados
    private Map<String, Inventory> userInventories = new HashMap<>();

    // Método para buscar o inventário de um usuário pelo nome de usuário (login)
    public Inventory getInventoryByUser(String username) {
        return userInventories.getOrDefault(username, null);
    }

    // Método para atualizar o inventário de um usuário
    public void updateInventory(String username, Inventory inventory) {
        userInventories.put(username, inventory);
    }


    public List<BoxDTO> getBoxesByUserId(Long userId) {
        Inventory inventory = inventoryRepository.findByUser_Id(userId);
        List<Box> boxes = inventory.getBoxes();

        List<BoxDTO> boxDTOs = boxes.stream()
            .map(box -> new BoxDTO(box.getType(), box.getPrice()))
            .collect(Collectors.toList());

        return boxDTOs;
    }

}
