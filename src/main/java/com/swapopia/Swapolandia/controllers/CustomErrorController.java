package com.swapopia.Swapolandia.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.swapopia.Swapolandia.model.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        // Aqui você pode criar um objeto ErrorResponse personalizado para fornecer detalhes sobre o erro de validação.
        // Por exemplo:
        ErrorResponse errorResponse = new ErrorResponse("Erro de validação", ex.getMessage());
        return errorResponse;
    }

    // Adicione mais métodos para tratar outros tipos de erros, se necessário.
}