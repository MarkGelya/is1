package ru.itmo.markgelya.is1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id @GeneratedValue
    Long id;

    private String nickname;

    private String email;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserData> notes = new ArrayList<>();

}
