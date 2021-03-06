package pl.dabal.accountaggregator.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.service.AliorOpenApiService;

@Slf4j
@RestController
@RequestMapping("/public/consent")
@AllArgsConstructor
final class PublicConsentController {

    private AliorOpenApiService aliorOpenApiService;


    @GetMapping("/redirect")
    public void retrieveOauthToken(@RequestParam String code, @RequestParam String state) {
        String token = null;
        aliorOpenApiService.authorizeConsent(code, state);
//    return token;
    }


}
