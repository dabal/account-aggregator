
package pl.dabal.accountaggregator.model.pojo.json;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "scopeUsageLimit"
})
@Builder
public class AisGetTransactionDetail {

    @JsonProperty("scopeUsageLimit")
    private String scopeUsageLimit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("scopeUsageLimit")
    public String getScopeUsageLimit() {
        return scopeUsageLimit;
    }

    @JsonProperty("scopeUsageLimit")
    public void setScopeUsageLimit(String scopeUsageLimit) {
        this.scopeUsageLimit = scopeUsageLimit;
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
