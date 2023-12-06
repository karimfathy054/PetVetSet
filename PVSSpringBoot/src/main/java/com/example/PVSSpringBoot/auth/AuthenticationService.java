package com.example.PVSSpringBoot.auth;

import com.example.PVSSpringBoot.Entities.Role;
import com.example.PVSSpringBoot.Entities.User;
import com.example.PVSSpringBoot.config.JwtService;
import com.example.PVSSpringBoot.repositories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsersRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .user_name(request.getUserName())
                .email(request.getEmail())
//                .password(request.getPassword())
                .password(passwordEncoder.encode(request.getPassword()))
                .is_admin(false)
                .join_date(new Date(System.currentTimeMillis()))
                .profile_photo(null)
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("Can you survive");
        System.out.println("emailll: "+request.getEmail());
        System.out.println(request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        System.out.println("Heere");
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println("email: "+request.getEmail());
        System.out.println(request.getPassword());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
