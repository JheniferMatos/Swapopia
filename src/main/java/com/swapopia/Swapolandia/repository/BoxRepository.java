package com.swapopia.Swapolandia.repository;

import java.util.List;
import com.swapopia.Swapolandia.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    
    List<Box> findByPriceLessThan(double price);
}
