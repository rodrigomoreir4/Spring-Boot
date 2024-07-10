package com.rodrigomoreira.myapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigomoreira.myapp.domain.Profiles.Profile;
import com.rodrigomoreira.myapp.dtos.AuthenticationDTO;
import com.rodrigomoreira.myapp.dtos.LoginResponseDTO;
import com.rodrigomoreira.myapp.dtos.RegisterDTO;
import com.rodrigomoreira.myapp.infra.security.TokenService;
import com.rodrigomoreira.myapp.repositories.ProfileRepository;
import com.rodrigomoreira.myapp.services.AuthorizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Profile) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
        if(profileRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Profile newProfile = new Profile(data.login(), encryptedPassword, data.role());

        profileRepository.save(newProfile);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Long id){
        Profile profile = authorizationService.getProfile(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    public ResponseEntity<?> getAllProfiles (){
        List<Profile> profiles = authorizationService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProfile(@PathVariable("id") Long id){
        if(id == 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Operation not permitted");
        authorizationService.removeProfile(id);
        return ResponseEntity.noContent().build();
    }
}
