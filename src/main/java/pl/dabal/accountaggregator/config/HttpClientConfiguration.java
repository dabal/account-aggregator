package pl.dabal.accountaggregator.config;

import ch.qos.logback.access.servlet.TeeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.util.Arrays;

@Configuration
public class HttpClientConfiguration {


    @Bean
    public HttpClient httpClient()
    {
        HttpClient client = HttpClient.newBuilder()
                // .sslContext(sslContext)
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                //.proxy(ProxySelector.of(new InetSocketAddress("localhost", 5555)))
                //.authenticator(Authenticator.getDefault())
                .build();

        return client;
    }
}