package pl.dabal.accountaggregator.model.json;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "scopeUsageLimit",
        "maxAllowedHistoryLong"
})
public class AisGetTransactionsCancelled {

    @JsonProperty("scopeUsageLimit")
    private String scopeUsageLimit;
    @JsonProperty("maxAllowedHistoryLong")
    private Integer maxAllowedHistoryLong;
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

    @JsonProperty("maxAllowedHistoryLong")
    public Integer getMaxAllowedHistoryLong() {
        return maxAllowedHistoryLong;
    }

    @JsonProperty("maxAllowedHistoryLong")
    public void setMaxAllowedHistoryLong(Integer maxAllowedHistoryLong) {
        this.maxAllowedHistoryLong = maxAllowedHistoryLong;
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
