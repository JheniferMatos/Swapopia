package com.rpg.swapopia.controllers;

import com.rpg.swapopia.domain.item.Inventory;
import com.rpg.swapopia.domain.user.User;
import com.rpg.swapopia.domain.user.dto.AuthenticationDTO;
import com.rpg.swapopia.domain.user.dto.LoginResponseDTO;
import com.rpg.swapopia.domain.user.dto.RegisterDTO;
import com.rpg.swapopia.domain.user.dto.UpdateUserDTO;
import com.rpg.swapopia.infra.security.TokenService;
import com.rpg.swapopia.repositories.InventoryRepository;
import com.rpg.swapopia.repositories.UserRepository;
 
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());
        newUser.setCash(100.0f);

        this.repository.save(newUser);

        // Criação e configuração do inventário
        Inventory inventory = new Inventory();
        inventory.setUser(newUser); // Associa o inventário ao novo usuário
        // Configurar outros detalhes iniciais do inventário, se necessário
        // ...

        // Salvar o inventário no repositório correspondente
        inventoryRepository.save(inventory);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserDTO updateInfo, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User authenticatedUser = (User) authentication.getPrincipal();

        try {
            Field[] fields = updateInfo.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(updateInfo);
                if (value != null && !field.getName().equals("password")) {
                    Field userField = authenticatedUser.getClass().getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    userField.set(authenticatedUser, value);
                }
            }

            String newPassword = updateInfo.password();
            if (newPassword != null) {
                // Encriptografar a nova senha antes de atualizar
                String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
                Field userPasswordField = authenticatedUser.getClass().getDeclaredField("password");
                userPasswordField.setAccessible(true);
                userPasswordField.set(authenticatedUser, encryptedPassword);
            }

            userRepository.save(authenticatedUser);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Tratar exceções adequadamente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
