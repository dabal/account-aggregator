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
import pl.dabal.accountaggregator.model.pojo.json.*;
import pl.dabal.accountaggregator.repository.ConsentRepository;

import java.io.IOException;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AliorOpenApiService {

    private ConsentRepository consentRepository;
    private HttpClient httpClient;
    private AliorProperties aliorProperties;
    private AliorOpenApiInvoker aliorOpenApiInvoker;

    public AliorOpenApiRequest buildOpenApiAuthAuthorizeRequestBody(Consent consent) {
//        String uuid = Generators.timeBasedGenerator().generate().toString();
//        String state = Generators.timeBasedGenerator().generate().toString();
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

    public String createConsent(@AuthenticationPrincipal User user) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
        String uuid = Generators.timeBasedGenerator().generate().toString();
        String state = Generators.timeBasedGenerator().generate().toString();
        Consent consent=new Consent(user, uuid,state,aliorProperties.getScopeTimeLimitInDays());
        consentRepository.save(consent);
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(buildOpenApiAuthAuthorizeRequestBody(consent));
            log.debug(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String result = aliorOpenApiInvoker.invoke(user, uuid, buildOpenApiAuthAuthorizeRequestBody(consent));
        return result;
    }

}
