package pl.dabal.accountaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dabal.accountaggregator.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByAccountNumber(String accountNumber);
}

