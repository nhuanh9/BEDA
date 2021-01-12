package com.example.datn.service;

import com.example.datn.model.VerificationToken;

public interface
VerificationTokenService {
    VerificationToken findByToken(String token);

    void save(VerificationToken token);
}