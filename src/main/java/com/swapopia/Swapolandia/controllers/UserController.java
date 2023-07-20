package com.swapopia.Swapolandia.controllers;

import com.swapopia.Swapolandia.repository.UserRepository;
import com.swapopia.Swapolandia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;

//import java.util.Optional;
//import java.util.UUID;
//import java.io.IOException;
import java.util.List;
//import java.io.File;

@RestController
@RequestMapping("/user")

public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Endpoint para buscar todos os usuários
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint para buscar um usuário pelo login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginUser) {
        User user = userRepository.findByLogin(loginUser.getLogin());

        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login bem-sucedido", HttpStatus.OK);
    }

    // Endpoint para registrar um usuário
    // validação nao está funcionando
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getDefaultMessage()).append(", ");
            }
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length()); // Remover a última vírgula e espaço
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        // Verificar se o usuário já está cadastrado
        if (userRepository.findByLogin(user.getLogin()) != null) {
            return new ResponseEntity<>("Usuário já cadastrado", HttpStatus.BAD_REQUEST);
        }

        // Salvar o usuário no banco de dados
        userRepository.save(user);

        return new ResponseEntity<>("Usuário registrado com sucesso", HttpStatus.OK);
    }

    /*@PostMapping("/uploadProfilePicture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {
        try {
            // Verificar se o usuário com o ID fornecido existe no banco de dados
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
            }

            User user = optionalUser.get();

            // Verificar se o arquivo é uma imagem
            if (file == null || !file.getContentType().startsWith("image/")) {
                return new ResponseEntity<>("O arquivo não é uma imagem válida", HttpStatus.BAD_REQUEST);
            }

            // Salvar a foto no diretório de imagens (você pode personalizar o diretório
            // conforme sua necessidade)
            String imagePath = "C:/uploads/profile_pictures/";
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File dest = new File(imagePath + fileName);
            file.transferTo(dest);

            // Atualizar o caminho da foto de perfil do usuário no banco de dados
            user.setProfilePicture(fileName);
            userRepository.save(user);

            return new ResponseEntity<>("Foto de perfil carregada com sucesso", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Erro ao fazer o upload da foto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

}