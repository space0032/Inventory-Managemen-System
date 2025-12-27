package com.inventory.ims.service;

import com.inventory.ims.dto.RegisterRequest;
import com.inventory.ims.entity.Role;
import com.inventory.ims.entity.User;
import com.inventory.ims.repository.RoleRepository;
import com.inventory.ims.repository.UserRepository;
import com.inventory.ims.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFullName("Test User");

        Role workerRole = new Role("WORKER", "Default worker role");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(roleRepository.findByName("WORKER")).thenReturn(Optional.of(workerRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        String result = authService.register(request);

        assertEquals("User registered successfully", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertEquals("Username already exists", exception.getMessage());
    }
}