package org.example.diploma_jwt.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.playload.request.JwtRequest;
import org.example.diploma_jwt.playload.request.RefreshJwtRequest;
import org.example.diploma_jwt.playload.request.SignUpRequest;
import org.example.diploma_jwt.playload.response.JwtResponse;
import org.example.diploma_jwt.playload.response.MessageResponse;
import org.example.diploma_jwt.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ResponseBody
    @PostMapping("register")
    public JwtResponse register(@ModelAttribute @Valid SignUpRequest request) {
        return authService.register(request);
    }

    @ResponseBody
    @PostMapping("login")
    public JwtResponse login(@ModelAttribute @Valid JwtRequest authRequest) {
        return authService.login(authRequest);
    }

    @ResponseBody
    @PostMapping("token")
    public JwtResponse getNewAccessToken(@RequestBody @Valid RefreshJwtRequest request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @ResponseBody
    @PostMapping("refresh")
    public JwtResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        return authService.refresh(request.getRefreshToken());
    }

}