package pl.dabal.accountaggregator.service;

import org.json.simple.parser.ParseException;
import pl.dabal.accountaggregator.model.User;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface ConsentGetOAuthLinkService {
    String createConsent(User user) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException;

 String getConsentCreateJSON(String uuid, String state) ;

}
