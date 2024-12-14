package ltweb.controller;

import ltweb.dto.UserProfileResponse;
import ltweb.security.JwtTokenUtil;
import ltweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Bỏ "Bearer "
        String email = JwtTokenUtil.getEmailFromToken(token);
        UserProfileResponse profile = userService.getUserProfile(email);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Xử lý logout nếu lưu JWT vào cơ sở dữ liệu hoặc localStorage
        return ResponseEntity.ok("Logged out successfully!");
    }
}
