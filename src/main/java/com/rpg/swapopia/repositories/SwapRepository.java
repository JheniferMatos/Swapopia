package com.rpg.swapopia.repositories;

import com.rpg.swapopia.domain.user.Swap;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SwapRepository extends JpaRepository<Swap, Long> {


    
}
