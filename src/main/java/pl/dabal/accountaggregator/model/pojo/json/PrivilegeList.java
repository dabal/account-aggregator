
package pl.dabal.accountaggregator.model.pojo.json;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ais:getTransactionsDone",
        "ais:getTransactionsPending",
        "ais:getTransactionsRejected",
        "ais:getTransactionsCancelled",
        "ais:getTransactionsScheduled",
        "ais:getTransactionDetail"
})
@Builder
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
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ais:getTransactionsDone")
    public AisGetTransactionsDone getAisGetTransactionsDone() {
        return aisGetTransactionsDone;
    }

    @JsonProperty("ais:getTransactionsDone")
    public void setAisGetTransactionsDone(AisGetTransactionsDone aisGetTransactionsDone) {
        this.aisGetTransactionsDone = aisGetTransactionsDone;
    }

    @JsonProperty("ais:getTransactionsPending")
    public AisGetTransactionsPending getAisGetTransactionsPending() {
        return aisGetTransactionsPending;
    }

    @JsonProperty("ais:getTransactionsPending")
    public void setAisGetTransactionsPending(AisGetTransactionsPending aisGetTransactionsPending) {
        this.aisGetTransactionsPending = aisGetTransactionsPending;
    }

    @JsonProperty("ais:getTransactionsRejected")
    public AisGetTransactionsRejected getAisGetTransactionsRejected() {
        return aisGetTransactionsRejected;
    }

    @JsonProperty("ais:getTransactionsRejected")
    public void setAisGetTransactionsRejected(AisGetTransactionsRejected aisGetTransactionsRejected) {
        this.aisGetTransactionsRejected = aisGetTransactionsRejected;
    }

    @JsonProperty("ais:getTransactionsCancelled")
    public AisGetTransactionsCancelled getAisGetTransactionsCancelled() {
        return aisGetTransactionsCancelled;
    }

    @JsonProperty("ais:getTransactionsCancelled")
    public void setAisGetTransactionsCancelled(AisGetTransactionsCancelled aisGetTransactionsCancelled) {
        this.aisGetTransactionsCancelled = aisGetTransactionsCancelled;
    }

    @JsonProperty("ais:getTransactionsScheduled")
    public AisGetTransactionsScheduled getAisGetTransactionsScheduled() {
        return aisGetTransactionsScheduled;
    }

    @JsonProperty("ais:getTransactionsScheduled")
    public void setAisGetTransactionsScheduled(AisGetTransactionsScheduled aisGetTransactionsScheduled) {
        this.aisGetTransactionsScheduled = aisGetTransactionsScheduled;
    }

    @JsonProperty("ais:getTransactionDetail")
    public AisGetTransactionDetail getAisGetTransactionDetail() {
        return aisGetTransactionDetail;
    }

    @JsonProperty("ais:getTransactionDetail")
    public void setAisGetTransactionDetail(AisGetTransactionDetail aisGetTransactionDetail) {
        this.aisGetTransactionDetail = aisGetTransactionDetail;
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
