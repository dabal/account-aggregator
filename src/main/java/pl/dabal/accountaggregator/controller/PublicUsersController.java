package pl.dabal.accountaggregator.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.UserDto;
import pl.dabal.accountaggregator.repository.UserRepository;
import pl.dabal.accountaggregator.service.UserAuthenticationService;


import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
@Slf4j
@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
  @NonNull
  UserAuthenticationService authentication;
  @NonNull
  UserRepository users;

  @PostMapping("/register")
  String register(
          @RequestBody UserDto userDto) {
    log.debug(userDto.toString());
    users
      .save(
        User
          .builder()
          .lastName(userDto.getLastName())
          .firstName(userDto.getFirstName())
          .password(userDto.getPassword())
          .email(userDto.getEmail())
          .build()
      );

    return login(userDto);
  }


  @PostMapping("/login")
  String login(
          @RequestBody UserDto userDto) {
    return authentication
      .login(userDto.getEmail(), userDto.getPassword())
      .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
  }
}
