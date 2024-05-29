package com.example.timeder.auth;

import com.example.timeder.config.JwtService;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import com.example.timeder.user.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .index(request.getIndex())
                .build();

        this.repository.save(user);

        var jwtToken = this.jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = this.repository.findByIndex(Integer.valueOf(request.getEmail()))
                .orElseThrow();
        var jwtToken = this.jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
