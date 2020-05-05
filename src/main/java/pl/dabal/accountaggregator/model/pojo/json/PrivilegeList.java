
package pl.dabal.accountaggregator.model.pojo.json;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ais:getTransactionsDone",
        "ais:getTransactionsPending",
        "ais:getTransactionsRejected",
        "ais:getTransactionsCancelled",
        "ais:getTransactionsScheduled",
        "ais:getTransactionDetail",
        "accountNumber"
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrivilegeList {

    @JsonProperty("ais:getTransactionsDone")
    private AisGetTransactionsDone aisGetTransactionsDone;
    @JsonProperty("ais:getTransactionsPending")
    private AisGetTransactionsPending aisGetTransactionsPending;
    @JsonProperty("ais:getTransactionsRejected")
    private AisGetTransactionsRejected aisGetTransactionsRejected;
    @JsonProperty("ais:getTransactionsCancelled")
    private AisGetTransactionsCancelled aisGetTransactionsCancelled;
    @JsonProperty("ais:getTransactionsScheduled")
    private AisGetTransactionsScheduled aisGetTransactionsScheduled;
    @JsonProperty("ais:getTransactionDetail")
    private AisGetTransactionDetail aisGetTransactionDetail;
    @JsonProperty("accountNumber")
        private String accountNumber;
}
