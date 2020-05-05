package pl.dabal.accountaggregator.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.dto.TokenDto;
import pl.dabal.accountaggregator.service.AliorOpenApiService;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/consents")
public class SecuredConsentController {
    private AliorOpenApiService aliorOpenApiService;

    @GetMapping("add")
    public TokenDto test(@AuthenticationPrincipal User user) {
        String json = "";

            json = aliorOpenApiService.createConsent(user);

        return new TokenDto(json);
    }

}
