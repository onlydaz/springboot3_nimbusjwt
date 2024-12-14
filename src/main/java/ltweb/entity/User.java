package ltweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String avatar;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private LocalDate createdDate = LocalDate.now();
    private String role;
    private boolean enabled = false; // Tài khoản chưa được kích hoạt mặc định
}
