package pl.dabal.accountaggregator.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.dto.CredentialsDto;
import pl.dabal.accountaggregator.model.dto.TokenDto;
import pl.dabal.accountaggregator.model.dto.UserDto;
import pl.dabal.accountaggregator.repository.UserRepository;
import pl.dabal.accountaggregator.service.UserAuthenticationService;

import javax.validation.Valid;

import static lombok.AccessLevel.PACKAGE;

@Slf4j
@RestController
@RequestMapping("/public/users")

@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserRepository users;

    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    TokenDto register(
            @RequestBody @Valid UserDto userDto) {
        log.debug(userDto.toString());
        users
                .save(
                        User
                                .builder()
                                .lastName(userDto.getLastName())
                                .firstName(userDto.getFirstName())
                                .password(passwordEncoder.encode(userDto.getPassword()))
                                .email(userDto.getEmail())
                                .build()
                );

        //return login(userDto);
        return login(new CredentialsDto(userDto.getEmail(), userDto.getPassword()));
    }


    @PostMapping("/login")
    TokenDto login(
            @RequestBody @Valid CredentialsDto credentialsDTO) {
        return new TokenDto(authentication
                .login(credentialsDTO.getEmail(), credentialsDTO.getPassword())
                .orElseThrow(() -> new RuntimeException("invalid login and/or password")));
    }
}
