package com.example.vag.service;

import com.example.vag.model.User;
import com.example.vag.controller.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User register(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void delete(User user);
    User update(UserDTO userDTO);
    User getCurrentUser();
}