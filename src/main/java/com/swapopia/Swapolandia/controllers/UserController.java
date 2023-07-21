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
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
//import java.util.UUID;
//import java.io.IOException;
import java.util.List;
//import java.io.File;

import javax.validation.Valid;

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

    // Verificar se o usuário existe
    if (user == null) {
        return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
    }

    // Verificar se a senha fornecida pelo usuário corresponde à senha armazenada como hash no banco de dados
    if (BCrypt.checkpw(loginUser.getPassword(), user.getPassword())) {
        return new ResponseEntity<>("Login bem-sucedido!", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
    }
}

    // validação nao está funcionando
    // Endpoint para registrar um usuário
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user, BindingResult bindingResult) {
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

        //Gerar o hash da senha usando o BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Definir a senha do usuário como o hash gerado
        user.setPassword(hashedPassword);

        // Salvar o usuário no banco de dados
        userRepository.save(user);

        return new ResponseEntity<>("Usuário registrado com sucesso", HttpStatus.OK);
    }

    @PutMapping("/usuario")
    public ResponseEntity<String> updateUserProfile(@RequestBody User user) {
        // Verificar se o usuário com o ID fornecido existe no banco de dados
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        User existingUser = optionalUser.get();

        // Atualizar o nome do usuário
        existingUser.setName(user.getName());

        // Verificar se a senha foi fornecida e atualizar a senha do usuário
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }

        userRepository.save(existingUser);

        return new ResponseEntity<>("Perfil do usuário atualizado com sucesso", HttpStatus.OK);
    }

    /*
     * @PostMapping("/uploadProfilePicture")
     * public ResponseEntity<String> uploadProfilePicture(@RequestParam("file")
     * MultipartFile file,
     * 
     * @RequestParam("userId") Long userId) {
     * try {
     * // Verificar se o usuário com o ID fornecido existe no banco de dados
     * Optional<User> optionalUser = userRepository.findById(userId);
     * if (optionalUser.isEmpty()) {
     * return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
     * }
     * 
     * User user = optionalUser.get();
     * 
     * // Verificar se o arquivo é uma imagem
     * if (file == null || !file.getContentType().startsWith("image/")) {
     * return new ResponseEntity<>("O arquivo não é uma imagem válida",
     * HttpStatus.BAD_REQUEST);
     * }
     * 
     * // Salvar a foto no diretório de imagens (você pode personalizar o diretório
     * // conforme sua necessidade)
     * String imagePath = "C:/uploads/profile_pictures/";
     * String fileName = UUID.randomUUID().toString() + "_" +
     * file.getOriginalFilename();
     * File dest = new File(imagePath + fileName);
     * file.transferTo(dest);
     * 
     * // Atualizar o caminho da foto de perfil do usuário no banco de dados
     * user.setProfilePicture(fileName);
     * userRepository.save(user);
     * 
     * return new ResponseEntity<>("Foto de perfil carregada com sucesso",
     * HttpStatus.OK);
     * } catch (IOException e) {
     * return new ResponseEntity<>("Erro ao fazer o upload da foto",
     * HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     */

}