package com.example.datn.service;

import com.example.datn.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void save(User user);

    Iterable<User> findAll();

    User findByUsername(String username);

    User getCurrentUser();

    Optional<User> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(User user);

    boolean isRegister(User user);

    User findByEmail(String email);

    boolean isCorrectConfirmPassword(User user);

    void delete(User user);

    Iterable<User> findTopPosts();

    Iterable<User> findTopLinkDocs();

    Iterable<User> findTopComments();
}
