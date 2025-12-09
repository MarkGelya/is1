package ru.itmo.markgelya.is1.controller;

import ru.itmo.markgelya.is1.dto.LoginDTO;
import ru.itmo.markgelya.is1.dto.RegisterDTO;
import ru.itmo.markgelya.is1.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        authService.register(
            registerDTO.getUsername(),
            registerDTO.getPassword(),
            registerDTO.getNickname(),
            registerDTO.getEmail()
        );
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO.getUsername(), loginDTO.getPassword()));
    }

}
