package com.example.basicauthimplementation.v1.controller;


import com.example.basicauthimplementation.entity.Users;
import com.example.basicauthimplementation.v1.dto.LoginDTO;
import com.example.basicauthimplementation.v1.dto.RegisterDTO;
import com.example.basicauthimplementation.v1.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @GetMapping
    public ResponseEntity<List<Users>> findAll (){
        return ResponseEntity.ok(authService.showAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.ok("login test: " + authService.login(loginDTO, request, response));
    }
}
