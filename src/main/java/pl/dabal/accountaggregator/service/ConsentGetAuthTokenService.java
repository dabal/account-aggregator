package pl.dabal.accountaggregator.service;

import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.repository.AccountRepository;
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
import java.util.Iterator;

@Slf4j
@Service
@AllArgsConstructor
public class ConsentGetAuthTokenService {
    private static final String CLIENT_ID="239c108f-7713-4995-a2df-9d056d4e31b5";
    private static final String CLIENT_SECRET="tJ3sQ5lV0mU1sV0xC5jV1eD6bP7dT8rR8tQ3yO7wU7jK8rY1uM";

    private ConsentRepository consentRepository;
    private AccountService accountService;
    private HttpClient httpClient;
    private AliorProperties aliorProperties;


    public void  getAuth(String code, String state) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
        String uuid = Generators.timeBasedGenerator().generate().toString();

        String body = getTokenJSON(uuid, state, code);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gateway.developer.aliorbank.pl/openapipl/sb/v2_1_1.1/auth/v2_1_1.1/token"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("x-ibm-client-id", aliorProperties.getClientId())
                .header("x-ibm-client-secret", aliorProperties.getClientSecret())
                .header("x-jws-signature", "")
                .header("x-request-id", uuid)
                .header("Accept-Charset", "utf-8")
                .header("Accept-Encoding", "deflate")
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString(Charset.forName("utf-8")));//.ofString());
        log.debug(String.valueOf(response.statusCode()));
        log.debug(response.headers().map().toString());
        //     response.
        log.debug(response.body());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());
        log.debug(jsonObject.toJSONString());
        Consent consent=consentRepository.findByState(state).orElseThrow(()->new IllegalArgumentException());
        JSONArray privilegeList = (JSONArray)((JSONObject) jsonObject.get("scope_details")).get("privilegeList");
        Iterator objIter = privilegeList.iterator();
        while (objIter.hasNext()) {
            JSONObject json=(JSONObject)objIter.next();
            String accountNumber=(String)json.get("accountNumber");
           accountService.addAccount(accountNumber,consent);

        }


//        return (response.body());
    }

    private String getTokenJSON(String uuid, String state, String code) {

        String request= String.format("{\n" +
                "    \"requestHeader\": {\n" +
                "        \"requestId\": \"%s\",\n" +
                "        \"userAgent\": \"61\",\n" +
                "        \"ipAddress\": \"127.0.0.1\",\n" +
                "        \"sendDate\": \"2001-12-20T04:57:27.535Z\",\n" +
                "        \"tppId\": \"5380491953831936\",\n" +
                "        \"isCompanyContext\": true,\n" +
                "        \"psuIdentifierType\": \"2888236854673408\",\n" +
                "        \"psuIdentifierValue\": \"2515599754264576\",\n" +
                "        \"psuContextIdentifierType\": \"7580329839689728\",\n" +
                "        \"psuContextIdentifierValue\": \"8013549762772992\"\n" +
                "    },\n" +
                "    \"grant_type\": \"authorization_code\",\n" +
                "    \"Code\": \"%s\",\n" +
                "    \"response_type\": \"code\",\n" +
                "    \"scope\": \"ais\",\n" +
                "    \"scope_details\": {\n" +
                "        \"privilegeList\": [\n" +
                "            {\n" +
                "                \"ais:getTransactionsDone\": {\n" +
                "                    \"scopeUsageLimit\": \"multiple\",\n" +
                "                    \"maxAllowedHistoryLong\": 1258\n" +
                "                },\n" +
                "                \"ais:getTransactionsPending\": {\n" +
                "                    \"scopeUsageLimit\": \"multiple\",\n" +
                "                    \"maxAllowedHistoryLong\": 1258\n" +
                "                },\n" +
                "                \"ais:getTransactionsRejected\": {\n" +
                "                    \"scopeUsageLimit\": \"multiple\",\n" +
                "                    \"maxAllowedHistoryLong\": 1258\n" +
                "                },\n" +
                "                \"ais:getTransactionsCancelled\": {\n" +
                "                    \"scopeUsageLimit\": \"multiple\",\n" +
                "                    \"maxAllowedHistoryLong\": 1258\n" +
                "                },\n" +
                "                \"ais:getTransactionsScheduled\": {\n" +
                "                    \"scopeUsageLimit\": \"multiple\",\n" +
                "                    \"maxAllowedHistoryLong\": 1258\n" +
                "                },\n" +
                "                \"ais:getTransactionDetail\": {\n" +
                "                    \"scopeUsageLimit\": \"multiple\"\n" +
                "                }\n" +
                "            }\n" +
                "        ],\n" +
                "        \"scopeGroupType\": \"ais\",\n" +
                "        \"consentId\": \"%s\",\n" +
                "        \"scopeTimeLimit\": \"2020-05-02T16:31:03.848Z\",\n" +
                "        \"throttlingPolicy\": \"psd2Regulatory\"\n" +
                "    },     \n" +
                "    \"redirect_uri\": \"http://localhost:8080/public/consent/redirect\",\n" +
                "    \"client_id\": \"%s\",\n" +
                "    \"is_user_session\": false,\n" +
                "    \"state\": \"your state\"\n" +
                "   \n" +
                "}",uuid,code, uuid,aliorProperties.getClientId(), state);
        log.debug("REQUEST: "+request);
        return request;
    }}
