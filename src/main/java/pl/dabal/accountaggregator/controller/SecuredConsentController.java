package pl.dabal.accountaggregator.controller;

import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.dto.OAuthLink;
import pl.dabal.accountaggregator.service.ConsentGetOAuthLinkService;
import pl.dabal.accountaggregator.service.UserAuthenticationService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class SecuredConsentController {

  ConsentGetOAuthLinkService consentGetOAuthLinkService;

public  SecuredConsentController(ConsentGetOAuthLinkService consentGetOAuthLinkService){
    this.consentGetOAuthLinkService=consentGetOAuthLinkService;
  }

  @GetMapping("/add")
  public OAuthLink createConsent() {
  String link=null;
  try{
      link=consentGetOAuthLinkService.createConsent();
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
