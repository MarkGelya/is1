package ru.itmo.markgelya.is1.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String nickname;
    private String email;
}

