package com.logrex.jobSeeker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;
    private String password;
}