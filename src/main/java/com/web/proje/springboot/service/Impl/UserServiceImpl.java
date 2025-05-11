package com.web.proje.springboot.service.Impl;

import com.web.proje.springboot.dto.RegistrationDto;
import com.web.proje.springboot.entity.Role;
import com.web.proje.springboot.entity.User;
import com.web.proje.springboot.repository.RoleRepository;
import com.web.proje.springboot.repository.UserRepository;
import com.web.proje.springboot.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        // Şifreyi encryptlemek için spring security'i kullandık.
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        if (registrationDto.getProfileImage() != null && !registrationDto.getProfileImage().isEmpty()) {
            try {
                user.setProfileImage(registrationDto.getProfileImage().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Role role = roleRepository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
