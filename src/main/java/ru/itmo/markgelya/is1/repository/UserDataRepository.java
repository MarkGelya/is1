package ru.itmo.markgelya.is1.repository;

import ru.itmo.markgelya.is1.entity.UserData;
import ru.itmo.markgelya.is1.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    List<UserData> findByProfile(Profile profile);
}
