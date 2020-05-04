package pl.dabal.accountaggregator.service;

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

@Slf4j
@Service
@AllArgsConstructor
public class ConsentGetOAuthLinkServiceImplHttpClient implements ConsentGetOAuthLinkService {
    private static final String CLIENT_ID = "239c108f-7713-4995-a2df-9d056d4e31b5";
    private static final String CLIENT_SECRET = "tJ3sQ5lV0mU1sV0xC5jV1eD6bP7dT8rR8tQ3yO7wU7jK8rY1uM";

    private ConsentRepository consentRepository;
    private HttpClient httpClient;
    private AliorProperties aliorProperties;


    @Override
    public String createConsent(User user) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
        String uuid = Generators.timeBasedGenerator().generate().toString();
        String state = Generators.timeBasedGenerator().generate().toString();

        String body = getConsentCreateJSON(uuid, state);
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

        String aspspRedirectUri = (String) jsonObject.get("aspspRedirectUri");
        consentRepository.save(Consent.builder().name(uuid).user(user).state(state).build());
        return (aspspRedirectUri);
    }

    public String getConsentCreateJSON(String uuid, String state) {

        String request = String.format("{" +
                "    \"requestHeader\": {" +
                "        \"requestId\": \"%s\"," +
                "        \"userAgent\": \"61\"," +
                "        \"ipAddress\": \"127.0.0.1\"," +
                "        \"sendDate\": \"2001-12-20T04:57:27.535Z\"," +
                "        \"tppId\": \"5380491953831936\"," +
                "        \"isCompanyContext\": true," +
                "        \"psuIdentifierType\": \"2888236854673408\"," +
                "        \"psuIdentifierValue\": \"2515599754264576\"," +
                "        \"psuContextIdentifierType\": \"7580329839689728\"," +
                "        \"psuContextIdentifierValue\": \"8013549762772992\"" +
                "    }," +
                "    \"response_type\": \"code\"," +
                "    \"client_id\": \"%s\"," +
                "    \"redirect_uri\": \"http://localhost:8080/public/consent/redirect\"," +
                "    \"scope\": \"ais\"," +
                "    \"scope_details\": {" +
                "        \"privilegeList\": [" +
                "            {" +
                "                \"ais:getTransactionsDone\": {" +
                "                    \"scopeUsageLimit\": \"multiple\"," +
                "                    \"maxAllowedHistoryLong\": 1258" +
                "                }," +
                "                \"ais:getTransactionsPending\": {" +
                "                    \"scopeUsageLimit\": \"multiple\"," +
                "                    \"maxAllowedHistoryLong\": 1258" +
                "                }," +
                "                \"ais:getTransactionsRejected\": {" +
                "                    \"scopeUsageLimit\": \"multiple\"," +
                "                    \"maxAllowedHistoryLong\": 1258" +
                "                }," +
                "                \"ais:getTransactionsCancelled\": {" +
                "                    \"scopeUsageLimit\": \"multiple\"," +
                "                    \"maxAllowedHistoryLong\": 1258" +
                "                }," +
                "                \"ais:getTransactionsScheduled\": {" +
                "                    \"scopeUsageLimit\": \"multiple\"," +
                "                    \"maxAllowedHistoryLong\": 1258" +
                "                }," +
                "                \"ais:getTransactionDetail\": {" +
                "                    \"scopeUsageLimit\": \"multiple\"" +
                "                }" +
                "            }" +
                "        ]," +
                "        \"scopeGroupType\": \"ais\"," +
                "        \"consentId\": \"%s\"," +
                "        \"scopeTimeLimit\": \"2020-05-02T16:31:03.848Z\"," +
                "        \"throttlingPolicy\": \"psd2Regulatory\"" +
                "    }," +
                "    \"state\": \"%s\"" +
                "}", uuid, aliorProperties.getClientId(), uuid, state);
        ConsentGetOAuthLinkServiceImplHttpClient.log.debug("REQUEST: " + request);
        return request;
    }
}
