package com.rpg.swapopia.model.user.dto;

import com.rpg.swapopia.model.user.UserRole;

public record RegisterDTO(String name, String login, String password, UserRole role) {
    
}
