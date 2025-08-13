package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.LoginRequest;
import com.maroc_ouvrage.semployee.dto.UserDTO;
import com.maroc_ouvrage.semployee.mapper.UserMapper;
import com.maroc_ouvrage.semployee.model.User;
import com.maroc_ouvrage.semployee.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationManager authManager, UserService userService, UserMapper userMapper) {
        this.authManager = authManager;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            System.out.println("Setting security context authentication for user: " + authentication.getName());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            String sessionId = session.getId();

            return ResponseEntity.ok().body(Map.of("message", "Login successful", "sessionId", sessionId));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser(Authentication auth) {
        User user = userService.getUserByUsername(auth.getName()).orElseThrow();
        return userMapper.toDTO(user);
    }


}

