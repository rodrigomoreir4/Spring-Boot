package com.rodrigomoreira.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rodrigomoreira.myapp.domain.Profiles.Profile;
import com.rodrigomoreira.myapp.repositories.ProfileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return profileRepository.findByLogin(username);
    }

    public Profile getProfile(Long id){
        return profileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Profile not found"));
    }

    public List<Profile> getAllProfiles(){
         return profileRepository.findAll();
    }

    public void removeProfile (Long id){
        getProfile(id);
        profileRepository.deleteById(id);
    }

}
