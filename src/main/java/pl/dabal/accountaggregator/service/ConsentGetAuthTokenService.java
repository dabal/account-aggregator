package pl.dabal.accountaggregator.service;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface ConsentGetAuthTokenService {
    void getAuth(String code, String state) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException;

    String getConsentCreateJSON(String uuid, String state);
}
