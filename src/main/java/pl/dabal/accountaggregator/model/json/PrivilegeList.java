package pl.dabal.accountaggregator.model.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

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
