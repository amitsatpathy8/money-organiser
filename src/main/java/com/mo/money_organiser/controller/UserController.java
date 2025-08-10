package com.mo.money_organiser.controller;

import com.mo.money_organiser.model.User;
import com.mo.money_organiser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal User current) {
        if (current == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(current);
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal User current, @RequestBody User body) {
        if (current == null) return ResponseEntity.status(401).build();
        current.setName(body.getName());
        // do not allow email/password update here unless you implement it safely
        userService.save(current);
        return ResponseEntity.ok(current);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User current) {
        if (current == null) return ResponseEntity.status(401).build();
        userService.save(current); // optionally delete - implement as needed
        return ResponseEntity.ok().build();
    }
}

