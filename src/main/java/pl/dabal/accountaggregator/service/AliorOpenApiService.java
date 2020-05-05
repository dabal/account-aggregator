package pl.dabal.accountaggregator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.json.*;

import pl.dabal.accountaggregator.repository.ConsentRepository;

import java.io.IOException;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public interface AliorOpenApiService {

    public AliorOpenApiRequest buildOpenApiAuthAuthorizeRequestBody(Consent consent) ;
    public RequestHeader buildRequestHeader(Consent consent) ;
    public List<PrivilegeList> buildPrivilegeList() ;
    public ScopeDetails buildScopeDetails(Consent consent) ;
    public String createConsent(@AuthenticationPrincipal User user);
    public AliorOpenApiRequest buildOpenApiAuthTokenRequestBody(Consent consent, String requestId, String code);
    public String authorizeConsent(String authCode, String state);


}
