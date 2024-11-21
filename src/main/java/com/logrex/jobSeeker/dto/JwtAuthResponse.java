package com.logrex.jobSeeker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private String role;
}