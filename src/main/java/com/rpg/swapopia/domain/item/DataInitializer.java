package com.rpg.swapopia.domain.item;

import org.springframework.stereotype.Service;

import com.rpg.swapopia.repositories.BoxRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DataInitializer {

    private final BoxRepository boxRepository;

    public DataInitializer(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @PostConstruct
    public void initializeData() {
        createBox("Material", 25);
        createBox("Espada", 150);
        createBox("Arco", 200);
        createBox("Roupa", 90);
    }

    private void createBox(String type, float price) {
        if (boxRepository.findByType(type) == null) {
            Box box = new Box();
            box.setType(type);
            box.setPrice(price);
            boxRepository.save(box);
        }
    }
}
