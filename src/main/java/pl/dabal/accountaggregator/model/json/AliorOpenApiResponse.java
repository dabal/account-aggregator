package pl.dabal.accountaggregator.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "token_type",
        "access_token",
        "expires_in",
        "scope",
        "scope_details",
        "responseHeader",
        "aspspRedirectUri"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AliorOpenApiResponse {

    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("expires_in")
    public Integer expiresIn;
    @JsonProperty("scope")
    public String scope;
    @JsonProperty("scope_details")
    public ScopeDetails scopeDetails;
    @JsonProperty("responseHeader")
    public ResponseHeader responseHeader;
    @JsonProperty("aspspRedirectUri")
    @NotNull
    @NotBlank
    private String aspspRedirectUri;

}