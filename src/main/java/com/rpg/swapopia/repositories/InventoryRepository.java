package com.rpg.swapopia.repositories;



//import com.swapopia.Swapolandia.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rpg.swapopia.model.item.Inventory;
import com.rpg.swapopia.model.user.User;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    Inventory findByUser(User user);
    Inventory findByUser_Id(Long userId);

    // Método para salvar o InventoryItem no banco de dados
    // O próprio Spring Data JPA irá fornecer a implementação desse método
    <S extends Inventory> S save(S inventoryItem);

}
