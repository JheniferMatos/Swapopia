package com.rpg.swapopia.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.rpg.swapopia.domain.item.dto.CashRequestDTO;
import com.rpg.swapopia.domain.user.User;
import com.rpg.swapopia.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        User user = userService.getUserByLogin(login);

        return ResponseEntity.ok(user);
    }

    //Adicionar dinheiro
    @PutMapping("/addcash")
    public ResponseEntity<Void> addCashToCurrentUser(@RequestBody CashRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtém o nome de usuário do usuário autenticado

        userService.addCashToUser(username, request.getAmount());

        return ResponseEntity.ok().build();
    }
}
