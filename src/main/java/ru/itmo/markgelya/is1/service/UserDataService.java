package ru.itmo.markgelya.is1.service;

import ru.itmo.markgelya.is1.dto.UserDataDTO;
import ru.itmo.markgelya.is1.entity.UserData;
import ru.itmo.markgelya.is1.entity.Profile;
import ru.itmo.markgelya.is1.repository.UserDataRepository;
import ru.itmo.markgelya.is1.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDataService {
    private final ProfileRepository profileRepository;
    private final UserDataRepository userDataRepository;
    private final JwtService jwtService;


    public List<UserDataDTO> getUserData(String username) {
        Profile profile = profileRepository.findByUserUsername(username)
            .orElseThrow(() -> new RuntimeException("Profile not found"));
        return userDataRepository.findByProfile(profile)
            .stream()
            .map(p -> new UserDataDTO(p.getId(), p.getContent(), p.getProfile().getNickname()))
            .toList();
    }

    public UserData createUserData(String token, String content) {
        String username = jwtService.extractUsername(token);
        Profile profile = profileRepository.findByUserUsername(username)
            .orElseThrow(() -> new RuntimeException("Profile not found"));
        UserData note = UserData.builder().content(content).profile(profile).build();
        return userDataRepository.save(note);
    }
}