package com.rpg.swapopia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpg.swapopia.domain.user.User;
import com.rpg.swapopia.repositories.BoxRepository;
import com.rpg.swapopia.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import com.rpg.swapopia.domain.item.Box;
import com.rpg.swapopia.domain.item.Inventory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void comprarCaixa(User user, String type) {
        Box box = boxRepository.findByType(type);

        if (box == null) {
            throw new IllegalArgumentException("Caixa não encontrada.");
        }

        if (user.getCash() < box.getPrice()) {
            throw new IllegalArgumentException("Saldo insuficiente para comprar esta caixa.");
        }

        user.setCash(user.getCash() - box.getPrice()); // Apenas atualize o saldo

        Inventory userInventory = user.getInventory();
        userInventory.getBoxes().add(box);

        // Atualize ambos os lados do relacionamento
        box.setInventory(userInventory);
        user.setInventory(userInventory);

        userRepository.save(user); // Isso salvará tanto o usuário quanto as mudanças na box
    }

}
