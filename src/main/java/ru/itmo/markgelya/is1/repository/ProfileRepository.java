package ru.itmo.markgelya.is1.repository;

import ru.itmo.markgelya.is1.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserUsername(String username);
}
