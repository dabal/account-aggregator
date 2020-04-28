package pl.dabal.accountaggregator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.pojo.json.*;
import pl.dabal.accountaggregator.repository.ConsentRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AliorOpenApiService {

    private ConsentRepository consentRepository;
    private HttpClient httpClient;
    private AliorProperties aliorProperties;

    public AliorOpenApiRequest buildOpenApiAuthAuthorizeRequestBody(String uuid, String state){
//        String uuid = Generators.timeBasedGenerator().generate().toString();
//        String state = Generators.timeBasedGenerator().generate().toString();
        return AliorOpenApiRequest.builder()
                .requestHeader(buildRequestHeader(uuid))
                .responseType("code")
                .clientId(aliorProperties.getClientId())
                .redirectUri("http://localhost:8080/public/consent/redirect")//TODO: move to properties
        .scope("ais")
                .scopeDetails(buildScopeDetails(uuid))
                .state(state)
                .build();
    }





    public RequestHeader buildRequestHeader(String requestId){

        return RequestHeader.builder()
                .requestId(requestId)
                .userAgent("userAgent")//TODO: move to property
        .ipAddress("127.0.0.1")//TODO: make it from request context
        .sendDate(LocalDateTime.now())
                .tppId("5380491953831936")//TODO: move to properties
        .isCompanyContext(true)
                .psuIdentifierType("2888236854673408")//TODO: check documentation, thist probably should by som kind of arg
                .psuIdentifierValue("2515599754264576")//TODO: check documentation, thist probably should by som kind of arg
        .psuContextIdentifierType("7580329839689728")
                .psuContextIdentifierValue("8013549762772992")
                .build();


    }

    public List<PrivilegeList> buildPrivilegeList(){
    return Arrays.asList(PrivilegeList.builder()
            .aisGetTransactionsDone(AisGetTransactionsDone.builder().
                    maxAllowedHistoryLong(1258)//TODO: add to properties
                    .scopeUsageLimit("multiple").build()
            ).aisGetTransactionDetail(AisGetTransactionDetail.builder().scopeUsageLimit("multiple").build())
            .build());
    }

    public ScopeDetails buildScopeDetails(String consentId){
    return ScopeDetails.builder()
            .privilegeList(buildPrivilegeList())
            .scopeGroupType("ais")
            .consentId(consentId)
            .scopeTimeLimit(LocalDateTime.now().plusDays(90))//probably move 90 to properties
        .throttlingPolicy("psd2Regulatory")
            .build();
    }

    public String createConsent(User user) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
        String uuid = Generators.timeBasedGenerator().generate().toString();
        String state = Generators.timeBasedGenerator().generate().toString();

        String json="";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString( buildOpenApiAuthAuthorizeRequestBody(uuid, state));
            log.debug(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gateway.developer.aliorbank.pl/openapipl/sb/v2_1_1.1/auth/v2_1_1.1/authorize"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("x-ibm-client-id", aliorProperties.getClientId())
                .header("x-ibm-client-secret", aliorProperties.getClientSecret())
                .header("x-jws-signature", "")
                .header("x-request-id", uuid)
                .header("Accept-Charset", "utf-8")
                .header("Accept-Encoding", "deflate")
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString(Charset.forName("utf-8")));//.ofString());
        log.debug(String.valueOf(response.statusCode()));
        log.debug(response.headers().map().toString());
        //     response.
        log.debug(response.body());


       AuthAuthorizeResponse jsonResponse= mapper.readValue(response.body(),AuthAuthorizeResponse.class);//readerFor(AuthAuthorizeResponse.class).readValue(response.body());

        /*JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());
        log.debug(jsonObject.toJSONString());

        String aspspRedirectUri = (String) jsonObject.get("aspspRedirectUri");
        consentRepository.save(Consent.builder().name(uuid).user(user).state(state).build());*/

        return (jsonResponse.getAspspRedirectUri());
    }

}
