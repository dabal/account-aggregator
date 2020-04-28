
package pl.dabal.accountaggregator.model.pojo.json;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "privilegeList",
    "scopeGroupType",
    "consentId",
    "scopeTimeLimit",
    "throttlingPolicy"
})
@Builder
@Getter
@Setter
public class ScopeDetails {

    @JsonProperty("privilegeList")
    private List<PrivilegeList> privilegeList = null;
    @JsonProperty("scopeGroupType")
    private String scopeGroupType;
    @JsonProperty("consentId")
    private String consentId;
    @JsonProperty("scopeTimeLimit")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="UTC")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime scopeTimeLimit;
    @JsonProperty("throttlingPolicy")
    private String throttlingPolicy;

    /*@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("privilegeList")
    public List<PrivilegeList> getPrivilegeList() {
        return privilegeList;
    }

    @JsonProperty("privilegeList")
    public void setPrivilegeList(List<PrivilegeList> privilegeList) {
        this.privilegeList = privilegeList;
    }

    @JsonProperty("scopeGroupType")
    public String getScopeGroupType() {
        return scopeGroupType;
    }

    @JsonProperty("scopeGroupType")
    public void setScopeGroupType(String scopeGroupType) {
        this.scopeGroupType = scopeGroupType;
    }

    @JsonProperty("consentId")
    public String getConsentId() {
        return consentId;
    }

    @JsonProperty("consentId")
    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    @JsonProperty("scopeTimeLimit")
    public String getScopeTimeLimit() {
        return scopeTimeLimit;
    }

    @JsonProperty("scopeTimeLimit")
    public void setScopeTimeLimit(String scopeTimeLimit) {
        this.scopeTimeLimit = scopeTimeLimit;
    }

    @JsonProperty("throttlingPolicy")
    public String getThrottlingPolicy() {
        return throttlingPolicy;
    }

    @JsonProperty("throttlingPolicy")
    public void setThrottlingPolicy(String throttlingPolicy) {
        this.throttlingPolicy = throttlingPolicy;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
*/
}
