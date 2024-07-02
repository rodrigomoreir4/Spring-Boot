package com.rodrigomoreira.myapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomoreira.myapp.domain.users.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findUserByDocument(String document);
    
}
