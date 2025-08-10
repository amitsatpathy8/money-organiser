package com.mo.money_organiser.controller;

import com.mo.money_organiser.dto.LoginRequest;
import com.mo.money_organiser.dto.SignupRequest;
import com.mo.money_organiser.model.User;
import com.mo.money_organiser.security.JwtTokenProvider;
import com.mo.money_organiser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        User u = userService.register(request.getEmail(), request.getPassword(), request.getName());
        String token = jwtTokenProvider.createToken(u.getId(), u.getEmail());
        return ResponseEntity.ok().body(new java.util.HashMap<>() {{
            put("token", token);
            put("userId", u.getId());
        }});
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(java.util.Map.of("error", "Invalid credentials"));
        }
        User u = userOpt.get();
        String token = jwtTokenProvider.createToken(u.getId(), u.getEmail());
        return ResponseEntity.ok(java.util.Map.of("token", token, "userId", u.getId()));
    }
}
