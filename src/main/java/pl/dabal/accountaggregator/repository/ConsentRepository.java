package pl.dabal.accountaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;

import java.util.Optional;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
public Optional<Consent> findByState(String state);
    public Optional<Consent> findByName(String name);
}

