package com.swapopia.Swapolandia.repository;

import java.util.List;
//import com.swapopia.Swapolandia.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swapopia.Swapolandia.model.InventoryItem;
import com.swapopia.Swapolandia.model.User;


@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    
    List<InventoryItem> findByUser(User user);
}
