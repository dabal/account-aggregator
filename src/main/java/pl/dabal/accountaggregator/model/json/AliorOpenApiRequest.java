package pl.dabal.accountaggregator.model.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestHeader",
        "response_type",
        "client_id",
        "redirect_uri",
        "scope",
        "scope_details",
        "state",
        "grant_type",
        "Code",
        "is_user_session"
})
public class AliorOpenApiRequest {

    @JsonProperty("requestHeader")
    private RequestHeader requestHeader;
    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("response_type")
    private String responseType;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("scope_details")
    private ScopeDetails scopeDetails;
    @JsonProperty("state")
    private String state;
    @JsonProperty("is_user_session")
    private Boolean isUserSession;
//    @JsonIgnore
//    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//    @JsonProperty("requestHeader")
//    public RequestHeader getRequestHeader() {
//        return requestHeader;
//    }
//
//    @JsonProperty("requestHeader")
//    public void setRequestHeader(RequestHeader requestHeader) {
//        this.requestHeader = requestHeader;
//    }
//
//    @JsonProperty("response_type")
//    public String getResponseType() {
//        return responseType;
//    }
//
//    @JsonProperty("response_type")
//    public void setResponseType(String responseType) {
//        this.responseType = responseType;
//    }
//
//    @JsonProperty("client_id")
//    public String getClientId() {
//        return clientId;
//    }
//
//    @JsonProperty("client_id")
//    public void setClientId(String clientId) {
//        this.clientId = clientId;
//    }
//
//    @JsonProperty("redirect_uri")
//    public String getRedirectUri() {
//        return redirectUri;
//    }
//
//    @JsonProperty("redirect_uri")
//    public void setRedirectUri(String redirectUri) {
//        this.redirectUri = redirectUri;
//    }
//
//    @JsonProperty("scope")
//    public String getScope() {
//        return scope;
//    }
//
//    @JsonProperty("scope")
//    public void setScope(String scope) {
//        this.scope = scope;
//    }
//
//    @JsonProperty("scope_details")
//    public ScopeDetails getScopeDetails() {
//        return scopeDetails;
//    }
//
//    @JsonProperty("scope_details")
//    public void setScopeDetails(ScopeDetails scopeDetails) {
//        this.scopeDetails = scopeDetails;
//    }
//
//    @JsonProperty("state")
//    public String getState() {
//        return state;
//    }
//
//    @JsonProperty("state")
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    @JsonAnyGetter
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }
//
}
