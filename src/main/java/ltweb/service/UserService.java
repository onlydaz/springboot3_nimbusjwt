package ltweb.service;

import ltweb.dto.UserProfileResponse;
import ltweb.entity.User;
import ltweb.repository.UserRepository;
import ltweb.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String email, String password, String name) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already exists!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEnabled(false); // Chưa kích hoạt
        userRepository.save(user);

        // Gửi OTP
        String otp = OtpUtil.generateOtp();
        emailService.sendOtp(email, otp);

        // Lưu OTP vào bộ nhớ hoặc cache
    }

    public void verifyOtp(String email, String otp) {
        // Kiểm tra OTP (lấy từ cache hoặc bộ nhớ)
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setEnabled(true);
            userRepository.save(user.get());
        } else {
            throw new IllegalStateException("User not found!");
        }
    }
    
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("Invalid credentials!");
        }
        if (!user.isEnabled()) {
            throw new IllegalStateException("Account is not activated!");
        }
        try {
            return JwtTokenUtil.generateToken(user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public UserProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found!"));
        UserProfileResponse response = new UserProfileResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setAvatar(user.getAvatar());
        response.setBirthDate(user.getBirthDate());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setCreatedDate(user.getCreatedDate());
        response.setRole(user.getRole());
        return response;
    }

}
