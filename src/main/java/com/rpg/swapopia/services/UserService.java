package com.rpg.swapopia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.rpg.swapopia.model.user.User;
import com.rpg.swapopia.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //Atualizar usuário
    public User updateProfile(String userId, String newName, String newPhotoUrl) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setName(newName);
        user.setFoto(newPhotoUrl);
        return userRepository.save(user);
    }

    //Adicionar Fundos a um usuário
    @PreAuthorize("authentication.name == #login")
    public void addCashToUser(String login, float amount) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            // Atualize o saldo do usuário com o valor fornecido
            float currentBalance = user.getCash();
            user.setCash(currentBalance + amount);
            userRepository.save(user);
        }
    }

    public void sellItem(String userId, Long itemId) {
        // Lógica para vender o item, atualizar o dinheiro do usuário, etc.
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    // ...
}
