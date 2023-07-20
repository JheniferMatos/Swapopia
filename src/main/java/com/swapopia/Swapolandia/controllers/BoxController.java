package com.swapopia.Swapolandia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swapopia.Swapolandia.model.Box;
import com.swapopia.Swapolandia.repository.BoxRepository;


import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    @Autowired
    private BoxRepository boxRepository;

    // Endpoint para buscar todas as caixas
    @GetMapping
    public ResponseEntity<List<Box>> getAllBoxes() {
        List<Box> boxes = boxRepository.findAll();
        return new ResponseEntity<>(boxes, HttpStatus.OK);
    }

    // Outros endpoints para manipulação de caixas (criação, atualização e exclusão)

}
