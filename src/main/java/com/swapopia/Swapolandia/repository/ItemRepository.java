package com.swapopia.Swapolandia.repository;

import java.util.List;
import com.swapopia.Swapolandia.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findByPriceLessThanEqual(double price);
}
