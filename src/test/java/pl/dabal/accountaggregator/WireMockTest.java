package pl.dabal.accountaggregator;

import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@ExtendWith(WireMockExtension.class)
public class WireMockTest {

    @Managed
    WireMockServer wireMockServer=with(wireMockConfig().dynamicPort());

    @Test
    public void someWireMockTest(){
        wireMockServer.stubFor(get("/").willReturn(aResponse().withBody("ziomek").withStatus(200)));
        RestTemplate restTemplate=new RestTemplate();
        String pom=restTemplate.getForObject("http://localhost:"+wireMockServer.port(),String.class);
        verify(1,getRequestedFor(urlEqualTo("/")));
        Assertions.assertTrue(pom.contains("ziomek"));
    }

}
