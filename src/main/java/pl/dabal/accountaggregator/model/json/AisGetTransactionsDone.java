package pl.dabal.accountaggregator.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "scopeUsageLimit",
        "maxAllowedHistoryLong",
        "accountNumber"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AisGetTransactionsDone {

    @JsonProperty("accountNumber")
    public String accountNumber;
    @JsonProperty("scopeUsageLimit")
    private String scopeUsageLimit;
    @JsonProperty("maxAllowedHistoryLong")
    private Integer maxAllowedHistoryLong;
}
