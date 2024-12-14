package ltweb.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileResponse {
    private String email;
    private String name;
    private String avatar;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private LocalDate createdDate;
    private String role;
}
