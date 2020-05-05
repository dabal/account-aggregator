package pl.dabal.accountaggregator.service.impl;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.repository.UserRepository;
import pl.dabal.accountaggregator.service.UserAuthenticationService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
final class UUIDAuthenticationService implements UserAuthenticationService {
    @NonNull
    UserRepository users;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<String> login(final String email, final String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("invalid login and/or password"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            user.setToken(uuid);
            users.save(user);
        } else {
            throw new UsernameNotFoundException("invalid login and/or password");
        }

        return Optional.of(uuid);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return users.findByToken(token);
    }

    @Override
    public void logout(User user) {
        //User user=findByToken()

    }
}