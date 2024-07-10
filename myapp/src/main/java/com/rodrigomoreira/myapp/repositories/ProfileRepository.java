package com.rodrigomoreira.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.rodrigomoreira.myapp.domain.Profiles.Profile;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    UserDetails findByLogin(String login);
}
