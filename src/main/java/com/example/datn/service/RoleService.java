package com.example.datn.service;


import com.example.datn.model.Role;


public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}
