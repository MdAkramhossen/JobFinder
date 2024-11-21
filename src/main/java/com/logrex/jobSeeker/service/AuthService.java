package com.logrex.jobSeeker.service;

import com.logrex.jobSeeker.dto.JwtAuthResponse;
import com.logrex.jobSeeker.dto.LoginDto;
import com.logrex.jobSeeker.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}