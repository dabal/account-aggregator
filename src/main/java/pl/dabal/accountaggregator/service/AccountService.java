package pl.dabal.accountaggregator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.Account;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.repository.AccountRepository;
import pl.dabal.accountaggregator.repository.ConsentRepository;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;
    private ConsentRepository consentRepository;

    public void addAccount(String accountNumber, Consent consent) {
        //Consent consent=consentRepository.findByName(consentStr).get();
        Optional<Account> existedAccount = accountRepository.findByAccountNumber(accountNumber);
        if (!existedAccount.isPresent()) {
            accountRepository.save(Account.builder().accountNumber(accountNumber).consent(consent).build());
        }

    }

}
