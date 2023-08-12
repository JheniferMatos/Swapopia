package com.rpg.swapopia.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rpg.swapopia.model.item.Inventory;
import com.rpg.swapopia.model.item.Item;


public interface ItemRepository extends JpaRepository<Item, String> {
    Optional<Item> findByName(String name);


    void deleteByName(String name);

    
    List<Item> findByNameIn(List<String> itemNames);

    //findall
    List<Item> findAllByOrderByNameAsc();
    
    List<Item> findByInventory(Inventory inventory);

}
