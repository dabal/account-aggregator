
package pl.dabal.accountaggregator.model.pojo.json;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

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

    @JsonProperty("scopeUsageLimit")
    private String scopeUsageLimit;
    @JsonProperty("maxAllowedHistoryLong")
    private Integer maxAllowedHistoryLong;
    @JsonProperty("accountNumber")
    public String accountNumber;
}
