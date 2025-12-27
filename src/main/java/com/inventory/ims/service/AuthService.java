package com.inventory.ims.service;

import com.inventory.ims.dto.LoginRequest;
import com.inventory.ims.dto.LoginResponse;
import com.inventory.ims.dto.RegisterRequest;
import com.inventory.ims.entity.Role;
import com.inventory.ims.entity.User;
import com.inventory.ims.repository.RoleRepository;
import com.inventory.ims.repository.UserRepository;
import com.inventory.ims.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Get or create WORKER role
        Role workerRole = roleRepository.findByName("WORKER")
                .orElseGet(() -> {
                    Role newRole = new Role("WORKER", "Default worker role");
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(workerRole);
        user.setIsActive(true);

        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is disabled");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername());
        return new LoginResponse(token, user.getUsername(), user.getRole().getName());
    }
}