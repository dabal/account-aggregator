package pl.dabal.accountaggregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dabal.accountaggregator.service.ConsentGetAuthTokenService;
import pl.dabal.accountaggregator.service.ConsentGetAuthTokenServiceImplHttpClient;
import pl.dabal.accountaggregator.service.ConsentGetOAuthLinkService;
import pl.dabal.accountaggregator.service.ConsentGetOAuthLinkServiceImplHttpClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/public/consent")
final class PublicConsentController {

    private ConsentGetOAuthLinkService consentGetOAuthLinkService;
    private ConsentGetAuthTokenService consentGetAuthTokenService;

    public PublicConsentController(ConsentGetOAuthLinkServiceImplHttpClient consentGetOAuthLinkService, ConsentGetAuthTokenServiceImplHttpClient consentGetAuthTokenService) {
        this.consentGetOAuthLinkService = consentGetOAuthLinkService;
        this.consentGetAuthTokenService = consentGetAuthTokenService;
    }


    @GetMapping("/redirect")
    public void retrieveOauthToken(@RequestParam String code, @RequestParam String state) {
        String token = null;
        try {
            //token=  consentGetAuthTokenService.getAuth(code,state);
            consentGetAuthTokenService.getAuth(code, state);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//    return token;
    }


}
