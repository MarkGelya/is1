package ru.itmo.markgelya.is1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDataDTO {
    private Long id;
    private String content;
    private String profile;
}
