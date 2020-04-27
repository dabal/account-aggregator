package pl.dabal.accountaggregator.controller;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.dto.OAuthLink;
import pl.dabal.accountaggregator.service.ConsentGetOAuthLinkServiceImplHttpClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/consents")
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class SecuredConsentController {

  ConsentGetOAuthLinkServiceImplHttpClient consentGetOAuthLinkService;

public  SecuredConsentController(ConsentGetOAuthLinkServiceImplHttpClient consentGetOAuthLinkService){
    this.consentGetOAuthLinkService=consentGetOAuthLinkService;
  }

  @GetMapping("/add")
  public OAuthLink createConsent(@AuthenticationPrincipal User user) {
  String link=null;
    try{
      link=consentGetOAuthLinkService.createConsent(user);
    } catch (IOException e) {
    e.printStackTrace();
  } catch (NoSuchAlgorithmException e) {
    e.printStackTrace();
  } catch (InterruptedException e) {
    e.printStackTrace();
  } catch (ParseException e) {
    e.printStackTrace();
  } catch (KeyManagementException e) {
    e.printStackTrace();
  }
  return new OAuthLink(link);
  }




}
