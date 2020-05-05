package pl.dabal.accountaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dabal.accountaggregator.model.Consent;

import java.util.Optional;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    Optional<Consent> findByState(String state);

    Optional<Consent> findByName(String name);
}

