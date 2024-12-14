package ltweb.controller;

import ltweb.dto.LoginRequest;
import ltweb.dto.RegisterRequest;
import ltweb.security.JwtTokenUtil;
import ltweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok("Registration successful. Check your email for the OTP to activate your account.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        userService.verifyOtp(email, otp);
        return ResponseEntity.ok("Account activated successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token); // Trả về JWT token
    }
}
