package pl.dabal.accountaggregator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.pojo.json.AuthAuthorizeResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;

@Service
@Slf4j
@AllArgsConstructor
public class AliorOpenApiInvokerHttpClientImpl implements AliorOpenApiInvoker {


    private AliorProperties aliorProperties;
    private ObjectMapper objectMapper;
    private HttpClient httpClient;

    @Override
    public String invoke(User user, String requestId, String json) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gateway.developer.aliorbank.pl/openapipl/sb/v2_1_1.1/auth/v2_1_1.1/authorize"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("x-ibm-client-id", aliorProperties.getClientId())
                .header("x-ibm-client-secret", aliorProperties.getClientSecret())
                .header("x-jws-signature", "")
                .header("x-request-id", requestId)
                .header("Accept-Charset", "utf-8")
                .header("Accept-Encoding", "deflate")
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response =
                null;//.ofString());
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(Charset.forName("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(String.valueOf(response.statusCode()));
        log.debug(response.headers().map().toString());
        //     response.
        log.debug(response.body());


        AuthAuthorizeResponse jsonResponse=null;
        try {
            jsonResponse=objectMapper.readValue(response.body(),AuthAuthorizeResponse.class);//readerFor(AuthAuthorizeResponse.class).readValue(response.body());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /*JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());
        log.debug(jsonObject.toJSONString());

        String aspspRedirectUri = (String) jsonObject.get("aspspRedirectUri");
        consentRepository.save(Consent.builder().name(uuid).user(user).state(state).build());*/

        return (jsonResponse.getAspspRedirectUri());
    }
}
