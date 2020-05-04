package pl.dabal.accountaggregator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "bank.alior")
@Validated
@Data
public class AliorProperties {

    @NotBlank
    @NotNull
    private String clientSecret;

    @NotNull
    @NotBlank
    private String clientId;

    @NotNull
    @NotBlank
    private String userAgent;

    @NotNull
    @NotBlank
    private String tppId;

    @NotNull
    @NotBlank
    private String psuIdentifierType;

    @NotNull
    @NotBlank
    private String psuIdentifierValue;

    @NotNull
    @NotBlank
    private String psuContextIdentifierType;

    @NotNull
    @NotBlank
    private String psuContextIdentifierValue;

    @NotNull
    private Integer maxAllowedHistoryLong;

    @NotNull
    private Integer scopeTimeLimitInDays;

    @NotNull
    @NotBlank
    private String redirectUri;
}
