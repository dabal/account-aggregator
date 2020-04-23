package pl.dabal.accountaggregator.service;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {
  @NonNull
  UserRepository users;

  @Override
  public Optional<String> login(final String email, final String password) {
    final String uuid = UUID.randomUUID().toString();
    final User user =users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("invalid login and/or password"));
if(user.getPassword().equals(password)){
  user.setToken(uuid);
  users.save(user);
}
else{
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