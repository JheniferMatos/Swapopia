package com.rpg.swapopia.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.rpg.swapopia.domain.item.Box;
import com.rpg.swapopia.domain.user.User;
import com.rpg.swapopia.repositories.BoxRepository;
import com.rpg.swapopia.services.BoxService;

@RestController
@RequestMapping("/caixas")
public class BoxController {
    @Autowired
    private BoxRepository boxRepository;
    @Autowired
    private BoxService boxService;
    

    public BoxController(BoxRepository boxRepository, BoxService boxService) {
        this.boxRepository = boxRepository;
        this.boxService = boxService;
    }

    // Retornar todas as caixas
    @GetMapping
    public List<Box> getAllBoxes() {
        return boxRepository.findAll(); // Chama o método findAll para obter todas as caixas
    }

    // Comprar uma caixa
    @Transactional
    @PostMapping("/comprar")
    public ResponseEntity<String> comprarCaixa(Authentication authentication,
            @RequestBody Map<String, String> requestBody) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User authenticatedUser = (User) authentication.getPrincipal();
        String type = requestBody.get("type");

        try {
            boxService.comprarCaixa(authenticatedUser, type);
            return ResponseEntity.ok("Caixa comprada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao comprar caixa: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> createBox(@RequestBody Box request) {
        Box box = new Box();
        box.setType(request.getType());
        box.setPrice(request.getPrice());
        boxRepository.save(box);

        return ResponseEntity.ok("Box created successfully");
    }
}
