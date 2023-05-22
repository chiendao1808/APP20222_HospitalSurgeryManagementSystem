package com.app20222.app20222_backend.controllers.auth;

import com.app20222.app20222_backend.dtos.requests.login.LoginRequest;
import com.app20222.app20222_backend.dtos.responses.login.LoginResponse;
import com.app20222.app20222_backend.security.jwt.JwtUtils;
import com.app20222.app20222_backend.security.models.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    /**
     * Login API -> JWT Token
     */
    @PostMapping("/login")
    @Operation(summary = "Login vào hệ thống")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginInfo, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get user details
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails == null)
            throw new BadCredentialsException("Username/Password is incorrect");
        // Generate access and refresh tokens
        final String accessToken = jwtUtils.generateJwt(userDetails.getUser());
        final String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUser());
        if (Objects.isNull(accessToken) || Objects.isNull(refreshToken))
            throw new BadCredentialsException("Generate tokens error");
        return ResponseEntity.ok(LoginResponse.builder()
                .issuedAt(new Date())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(userDetails.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
                .build());
    }
}
