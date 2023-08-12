package com.rpg.swapopia.repositories;

import com.rpg.swapopia.domain.user.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);

    Optional<User> findById(String id);
    
    UserDetails deleteByLogin(String login);
}
