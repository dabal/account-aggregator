package pl.dabal.accountaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dabal.accountaggregator.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByToken(String token);
}

