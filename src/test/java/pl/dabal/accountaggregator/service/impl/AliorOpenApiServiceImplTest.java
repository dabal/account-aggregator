package pl.dabal.accountaggregator.service.impl;

import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.config.SecurityConfig;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.json.AliorOpenApiRequest;
import pl.dabal.accountaggregator.providers.TokenAuthenticationProvider;
import pl.dabal.accountaggregator.repository.ConsentRepository;
import pl.dabal.accountaggregator.service.AccountService;
import pl.dabal.accountaggregator.service.UserAuthenticationService;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ExtendWith(WireMockExtension.class)
@Slf4j
@ContextConfiguration(classes = AliorProperties.class)
@ComponentScan
@TestPropertySource(properties = {
        "bank.alior.authorize-url=http://localhost:8080/authorize",
        "bank.alior.token-url=http://localhost:8080/authorize"

})
@WebMvcTest
@Import({SecurityConfig.class,
        TokenAuthenticationProvider.class,
})
class AliorOpenApiServiceImplTest {
    @Managed
    WireMockServer wireMockServer = with(wireMockConfig().port(8080));
    @MockBean
    UserAuthenticationService userAuthenticationService;
    @Autowired
    AliorOpenApiServiceImpl aliorOpenApiService;
    @MockBean
    private ConsentRepository consentRepository;
    @MockBean
    private AccountService accountService;
    @Autowired
    private AliorProperties aliorProperties;
    @MockBean
    private AliorOpenApiInvokerImpl aliorOpenApiInvoker;

    @Test
    public void createConsentShouldReturnProperRedirectUri() {
        when(consentRepository.save(any(Consent.class))).thenReturn(new Consent());//thenAnswer(i -> i.getArguments()[0]);
        when(aliorOpenApiInvoker.invoke(anyString(), any(AliorOpenApiRequest.class), anyMap())).thenCallRealMethod();
        wireMockServer.stubFor(post("/authorize").willReturn(aResponse().withBody("{\n" +
                "    \"aspspRedirectUri\": \"https://oauthdemo.developer.aliorbank.pl/login?refConsentHash=15f5a2f32530265ca33d03c25b92b004fa72992d72bc825cb4fb3e34f2ccf5f1-def1bf9d3bb176b8e724bcd9748851ba985f4e9789092ee2c1fd091a8a6eaa1f\",\n" +
                "    \"responseHeader\": {\n" +
                "        \"requestId\": \"2f6a5c21-2688-1f6a-ab4b-93b611c7ec01\",\n" +
                "        \"sendDate\": \"2020-05-14T14:58:37.114Z\",\n" +
                "        \"isCallback\": false\n" +
                "    }\n" +
                "}").withStatus(200).withHeader("Content-Type", "application/json")));
        User user = User.builder()
                .id(1L)
                .email("testowy_email@email.com")
                .firstName("jan")
                .lastName("kowalski")
                .password("password")
                .token("token")
                .build();
        String result = aliorOpenApiService.createConsent(user);
        assertEquals("https://oauthdemo.developer.aliorbank.pl/login?refConsentHash=15f5a2f32530265ca33d03c25b92b004fa72992d72bc825cb4fb3e34f2ccf5f1-def1bf9d3bb176b8e724bcd9748851ba985f4e9789092ee2c1fd091a8a6eaa1f", result);
    }
}
