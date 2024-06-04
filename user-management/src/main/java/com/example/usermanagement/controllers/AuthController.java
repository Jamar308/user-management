package com.example.usermanagement.controllers;

import com.example.usermanagement.dto.LoginDto;
import com.example.usermanagement.service.AuthService;
import com.example.usermanagement.util.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String success = authService.login(loginDto);
        return new ResponseEntity<>(success,HttpStatus.OK);
    }
}
