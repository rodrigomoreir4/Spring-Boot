package com.rodrigomoreira.myapp.config;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rodrigomoreira.myapp.domain.Profiles.Profile;
import com.rodrigomoreira.myapp.domain.Profiles.ProfileRole;
import com.rodrigomoreira.myapp.repositories.ProfileRepository;

@Configuration
public class AppConfig implements CommandLineRunner {
    
    @Autowired
    private ProfileRepository profileRepository;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public AtomicLong idCounter(){
        return new AtomicLong(1);
    }

    @Override
    public void run(String... args) throws Exception {

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        Profile master = new Profile("rodrigomoreira", encryptedPassword, ProfileRole.MASTER);

        profileRepository.saveAll(Arrays.asList(master));

    }
}
