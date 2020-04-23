package pl.dabal.accountaggregator.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.UserDto;
import pl.dabal.accountaggregator.service.UserAuthenticationService;
import pl.dabal.accountaggregator.service.UserCrudService;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
  @NonNull
  UserAuthenticationService authentication;
  @NonNull
  UserCrudService users;

  @PostMapping("/register")
  String register(
          @RequestBody UserDto userDto) {
    users
      .save(
        User
          .builder()
          .id(userDto.getUsername())
          .username(userDto.getUsername())
          .password(userDto.getPassword())
          .build()
      );

    return login(userDto);
  }

  @PostMapping("/login")
  String login(
          @RequestBody UserDto userDto) {
    return authentication
      .login(userDto.getUsername(), userDto.getPassword())
      .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
  }
}
