package com.swapopia.Swapolandia.repository;

import java.util.List;
import com.swapopia.Swapolandia.model.Swap;
import com.swapopia.Swapolandia.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwapRepository extends JpaRepository<Swap, Long> {

    List<Swap> findBySender(User sender);
    
}
