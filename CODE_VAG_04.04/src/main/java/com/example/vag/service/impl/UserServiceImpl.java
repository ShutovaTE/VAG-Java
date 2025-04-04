package com.example.vag.service.impl;

import com.example.vag.model.Role;
import com.example.vag.model.User;
import com.example.vag.repository.RoleRepository;
import com.example.vag.repository.UserRepository;
import com.example.vag.service.UserService;
import com.example.vag.controller.dto.UserDTO;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean initialized = false;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initialized) {
            initializeRoles();
            initializeAdmin();
            initialized = true;
        }
    }

    private void initializeRoles() {
        if (roleRepository.findByName(Role.RoleName.ADMIN).isEmpty()) {
            roleRepository.save(new Role(Role.RoleName.ADMIN));
        }
        if (roleRepository.findByName(Role.RoleName.ARTIST).isEmpty()) {
            roleRepository.save(new Role(Role.RoleName.ARTIST));
        }
    }

    private void initializeAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@vag.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(roleRepository.findByName(Role.RoleName.ADMIN)
                    .orElseThrow(() -> new IllegalStateException("ADMIN role not found")));
            userRepository.save(admin);
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User register(User user) {
        user.setRole(roleRepository.findByName(Role.RoleName.ARTIST).orElseThrow());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User update(UserDTO userDTO) {
        User user = getCurrentUser();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}