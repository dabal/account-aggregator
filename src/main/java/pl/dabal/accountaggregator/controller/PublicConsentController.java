package pl.dabal.accountaggregator.controller;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.model.dto.OAuthLink;
import pl.dabal.accountaggregator.service.ConsentGetOAuthLinkService;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/public/user")
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class PublicConsentController {

  ConsentGetOAuthLinkService consentGetOAuthLinkService;

public PublicConsentController(ConsentGetOAuthLinkService consentGetOAuthLinkService){
    this.consentGetOAuthLinkService=consentGetOAuthLinkService;
  }

  @GetMapping("/redirect")
  public String retrieveOauthToken(@RequestParam String code) {

  return code;
  }




}
