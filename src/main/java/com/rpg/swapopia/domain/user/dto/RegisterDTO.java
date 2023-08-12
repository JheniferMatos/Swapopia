package com.rpg.swapopia.domain.user.dto;

import com.rpg.swapopia.domain.user.UserRole;

public record RegisterDTO(String name, String login, String password, UserRole role) {
    
}
