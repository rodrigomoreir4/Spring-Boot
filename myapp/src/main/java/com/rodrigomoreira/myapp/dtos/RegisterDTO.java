package com.rodrigomoreira.myapp.dtos;

import com.rodrigomoreira.myapp.domain.Profiles.ProfileRole;

public record RegisterDTO(String login, String password, ProfileRole role) {

}