package com.swapopia.Swapolandia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swapopia.Swapolandia.model.Swap;
import com.swapopia.Swapolandia.repository.SwapRepository;

import java.util.List;

@RestController
@RequestMapping("/swap")
public class SwapController {
    
    private final SwapRepository swapRepository;

    @Autowired
    public SwapController(SwapRepository swapRepository) {
        this.swapRepository = swapRepository;
    }
    
    // Endpoint para buscar todas as propostas de troca
    @GetMapping
    public ResponseEntity<List<Swap>> getAllSwapProposals() {
        List<Swap> swapProposals = swapRepository.findAll();
        return new ResponseEntity<>(swapProposals, HttpStatus.OK);
    }

    // Outros endpoints para manipulação de propostas de troca (criação, atualização e exclusão)
}
