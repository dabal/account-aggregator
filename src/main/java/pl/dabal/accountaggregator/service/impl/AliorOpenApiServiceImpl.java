package pl.dabal.accountaggregator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.json.*;
import pl.dabal.accountaggregator.repository.ConsentRepository;
import pl.dabal.accountaggregator.service.AccountService;
import pl.dabal.accountaggregator.service.AliorOpenApiInvoker;
import pl.dabal.accountaggregator.service.AliorOpenApiService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AliorOpenApiServiceImpl implements AliorOpenApiService {

    private ConsentRepository consentRepository;
    private AccountService accountService;
    private AliorProperties aliorProperties;
    private AliorOpenApiInvoker aliorOpenApiInvoker;

    public AliorOpenApiRequest buildOpenApiAuthAuthorizeRequestBody(Consent consent) {
        return AliorOpenApiRequest.builder()
                .requestHeader(buildRequestHeader(consent))
                .responseType("code")
                .clientId(aliorProperties.getClientId())
                .redirectUri(aliorProperties.getRedirectUri())
                .scope("ais")
                .scopeDetails(buildScopeDetails(consent))
                .state(consent.getState())
                .build();
    }


    public RequestHeader buildRequestHeader(Consent consent) {

        return RequestHeader.builder()
                .requestId(consent.getName())
                .userAgent(aliorProperties.getUserAgent())
                .ipAddress("127.0.0.1")
                .sendDate(LocalDateTime.now())
                .tppId(aliorProperties.getTppId())
                .isCompanyContext(true)
                .psuIdentifierType(aliorProperties.getPsuIdentifierType())
                .psuIdentifierValue(aliorProperties.getPsuIdentifierValue())
                .psuContextIdentifierType(aliorProperties.getPsuContextIdentifierType())
                .psuContextIdentifierValue(aliorProperties.getPsuContextIdentifierValue())
                .build();


    }

    public List<PrivilegeList> buildPrivilegeList() {
        return Arrays.asList(PrivilegeList.builder()
                .aisGetTransactionsDone(AisGetTransactionsDone.builder().
                        maxAllowedHistoryLong(aliorProperties.getMaxAllowedHistoryLong())
                        .scopeUsageLimit("multiple").build()
                ).aisGetTransactionDetail(AisGetTransactionDetail.builder().scopeUsageLimit("multiple").build())
                .build());
    }

    public ScopeDetails buildScopeDetails(Consent consent) {
        return ScopeDetails.builder()
                .privilegeList(buildPrivilegeList())
                .scopeGroupType("ais")
                .consentId(consent.getName())
                .scopeTimeLimit(consent.getScopeTimeLimit())//probably move 90 to properties
                .throttlingPolicy("psd2Regulatory")
                .build();
    }

    public String createConsent(User user) {
        String uuid = Generators.timeBasedGenerator().generate().toString();
        String state = Generators.timeBasedGenerator().generate().toString();
        Consent consent = new Consent(user, uuid, state, aliorProperties.getScopeTimeLimitInDays());
        consentRepository.save(consent);
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(buildOpenApiAuthAuthorizeRequestBody(consent));
            log.debug(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        AliorOpenApiResponse response = aliorOpenApiInvoker.invoke(aliorProperties.getAuthorizeUrl(), buildOpenApiAuthAuthorizeRequestBody(consent),createHeaderMap(consent.getName()));
        return response.getAspspRedirectUri();
    }

    public AliorOpenApiRequest buildOpenApiAuthTokenRequestBody(Consent consent, String requestId, String code) {
        AliorOpenApiRequest aliorOpenApiAuthTokenRequest = buildOpenApiAuthAuthorizeRequestBody(consent);
        aliorOpenApiAuthTokenRequest.getRequestHeader().setRequestId(requestId);
        aliorOpenApiAuthTokenRequest.setGrantType("authorization_code");
        aliorOpenApiAuthTokenRequest.setCode(code);
        return aliorOpenApiAuthTokenRequest;
    }

    public String authorizeConsent(String authCode, String state) {
        Consent consent = consentRepository.findByState(state).get();
        String requestId = Generators.timeBasedGenerator().generate().toString();
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(buildOpenApiAuthTokenRequestBody(consent, requestId, authCode));
            log.debug(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        AliorOpenApiResponse response = aliorOpenApiInvoker.invoke(aliorProperties.getTokenUrl(), buildOpenApiAuthTokenRequestBody(consent, requestId, authCode),createHeaderMap(requestId));
        consent.setAccessToken(response.getAccessToken());
        consentRepository.save(consent);
        for (PrivilegeList privilege : response.getScopeDetails().getPrivilegeList()) {
            accountService.addAccount(privilege.getAccountNumber(), consent);

        }

        return null;
    }

    private Map<String,String> createHeaderMap(String requestId){
        Map<String,String> headersMap= new HashMap<>();
        headersMap.put("Content-Type", "application/json");
        headersMap.put("x-ibm-client-id", aliorProperties.getClientId());
        headersMap.put("x-ibm-client-secret", aliorProperties.getClientSecret());
        headersMap.put("x-jws-signature", "");
        headersMap.put("x-request-id", requestId);
        headersMap.put("Accept-Charset", "utf-8");
        headersMap.put("Accept-Encoding", "deflate");
        headersMap.put("accept", "application/json");
return headersMap;
    }

}
