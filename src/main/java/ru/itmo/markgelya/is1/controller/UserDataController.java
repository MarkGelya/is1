package ru.itmo.markgelya.is1.controller;

import ru.itmo.markgelya.is1.dto.CreateNoteDTO;
import ru.itmo.markgelya.is1.dto.UserDataDTO;
import ru.itmo.markgelya.is1.entity.UserData;
import ru.itmo.markgelya.is1.service.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserDataController {

    private final UserDataService noteService;

    public UserDataController(UserDataService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/user/data")
    public ResponseEntity<List<UserDataDTO>> getPosts(@RequestParam String username) {
        return ResponseEntity.ok(noteService.getUserData(username));
    }

    @PostMapping("/user/data")
    public ResponseEntity<?> createPost(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody CreateNoteDTO createNoteDTO
    ) {
        String token = authHeader.replace("Bearer ", "");
        UserData note = noteService.createUserData(token, createNoteDTO.getContent());
        return ResponseEntity.ok("Post created with id: " + note.getId());
    }


}