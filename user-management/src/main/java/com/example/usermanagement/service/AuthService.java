package com.example.usermanagement.service;

import com.example.usermanagement.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
