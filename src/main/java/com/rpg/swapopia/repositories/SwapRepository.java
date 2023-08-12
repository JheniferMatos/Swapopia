package com.rpg.swapopia.repositories;

import com.rpg.swapopia.model.user.Swap;
import com.rpg.swapopia.model.user.SwapStatus;
import com.rpg.swapopia.model.user.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SwapRepository extends JpaRepository<Swap, Long> {

     List<Swap> findByRecipientAndStatus(User recipient, SwapStatus status);
}

