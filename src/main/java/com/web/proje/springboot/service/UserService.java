package com.web.proje.springboot.service;

import com.web.proje.springboot.dto.RegistrationDto;
import com.web.proje.springboot.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    User findByEmail(String email);
}
