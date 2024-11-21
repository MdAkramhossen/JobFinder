package com.logrex.jobSeeker.service.impl;

import com.logrex.jobSeeker.dto.JwtAuthResponse;
import com.logrex.jobSeeker.dto.LoginDto;
import com.logrex.jobSeeker.dto.RegisterDto;
import com.logrex.jobSeeker.entity.Role;
import com.logrex.jobSeeker.entity.User;
import com.logrex.jobSeeker.exception.JobFinderAPIException;
import com.logrex.jobSeeker.exception.ResourceNotFoundException;
import com.logrex.jobSeeker.jpa.RoleRepository;
import com.logrex.jobSeeker.jpa.UserRepository;
import com.logrex.jobSeeker.security.JwtTokenProvider;
import com.logrex.jobSeeker.service.AuthService;
import com.logrex.jobSeeker.utils.USER_ROLE;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public String register(RegisterDto registerDto) {
        // check user email  is already exist

        if ( repository.existsByEmail(registerDto.getEmail())) {

            throw  new JobFinderAPIException(HttpStatus.BAD_REQUEST,"User with this email already exists !");
        }
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> roles= new HashSet<Role>();
        Role userRole= roleRepository.findByName(USER_ROLE.ROLE_JOBSEEKER.name())  .orElseThrow(() -> new ResourceNotFoundException("Role not found: ",USER_ROLE.ROLE_JOBSEEKER.name()));

        roles.add(userRole);

        user.setRoles(roles);
        repository.save(user);
        return "User registered successfully !";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        /// this method used for authenticate the user based on these credential
        Authentication authentication= authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        /// SecurityContextHolder() this method works like a central storage for the current user's security details for any future requests spring security can access the currect user's informantion
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setEmail(authentication.getName());
        jwtAuthResponse.setRole(authentication.getAuthorities().iterator().next().getAuthority());
        return jwtAuthResponse;
    }
}