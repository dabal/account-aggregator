package pl.dabal.accountaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dabal.accountaggregator.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);
}

