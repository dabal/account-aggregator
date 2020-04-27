
package pl.dabal.accountaggregator.model.pojo.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "requestId",
    "userAgent",
    "ipAddress",
    "sendDate",
    "tppId",
    "isCompanyContext",
    "psuIdentifierType",
    "psuIdentifierValue",
    "psuContextIdentifierType",
    "psuContextIdentifierValue"
})
public class RequestHeader {

    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("userAgent")
    private String userAgent;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("sendDate")
    private String sendDate;
    @JsonProperty("tppId")
    private String tppId;
    @JsonProperty("isCompanyContext")
    private Boolean isCompanyContext;
    @JsonProperty("psuIdentifierType")
    private String psuIdentifierType;
    @JsonProperty("psuIdentifierValue")
    private String psuIdentifierValue;
    @JsonProperty("psuContextIdentifierType")
    private String psuContextIdentifierType;
    @JsonProperty("psuContextIdentifierValue")
    private String psuContextIdentifierValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonProperty("userAgent")
    public String getUserAgent() {
        return userAgent;
    }

    @JsonProperty("userAgent")
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty("ipAddress")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @JsonProperty("sendDate")
    public String getSendDate() {
        return sendDate;
    }

    @JsonProperty("sendDate")
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @JsonProperty("tppId")
    public String getTppId() {
        return tppId;
    }

    @JsonProperty("tppId")
    public void setTppId(String tppId) {
        this.tppId = tppId;
    }

    @JsonProperty("isCompanyContext")
    public Boolean getIsCompanyContext() {
        return isCompanyContext;
    }

    @JsonProperty("isCompanyContext")
    public void setIsCompanyContext(Boolean isCompanyContext) {
        this.isCompanyContext = isCompanyContext;
    }

    @JsonProperty("psuIdentifierType")
    public String getPsuIdentifierType() {
        return psuIdentifierType;
    }

    @JsonProperty("psuIdentifierType")
    public void setPsuIdentifierType(String psuIdentifierType) {
        this.psuIdentifierType = psuIdentifierType;
    }

    @JsonProperty("psuIdentifierValue")
    public String getPsuIdentifierValue() {
        return psuIdentifierValue;
    }

    @JsonProperty("psuIdentifierValue")
    public void setPsuIdentifierValue(String psuIdentifierValue) {
        this.psuIdentifierValue = psuIdentifierValue;
    }

    @JsonProperty("psuContextIdentifierType")
    public String getPsuContextIdentifierType() {
        return psuContextIdentifierType;
    }

    @JsonProperty("psuContextIdentifierType")
    public void setPsuContextIdentifierType(String psuContextIdentifierType) {
        this.psuContextIdentifierType = psuContextIdentifierType;
    }

    @JsonProperty("psuContextIdentifierValue")
    public String getPsuContextIdentifierValue() {
        return psuContextIdentifierValue;
    }

    @JsonProperty("psuContextIdentifierValue")
    public void setPsuContextIdentifierValue(String psuContextIdentifierValue) {
        this.psuContextIdentifierValue = psuContextIdentifierValue;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
