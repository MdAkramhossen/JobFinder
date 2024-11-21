package com.logrex.jobSeeker.controller;

import com.logrex.jobSeeker.dto.JwtAuthResponse;
import com.logrex.jobSeeker.dto.LoginDto;
import com.logrex.jobSeeker.dto.RegisterDto;
import com.logrex.jobSeeker.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /// build register rest API

    @PostMapping("/register")
    public ResponseEntity <String> register (@Valid @RequestBody RegisterDto registerDto) {

        String response= authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login (@RequestBody LoginDto loginDto) {


        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        //    jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}