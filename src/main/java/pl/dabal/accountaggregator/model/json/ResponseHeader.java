package pl.dabal.accountaggregator.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "requestId",
        "sendDate",
        "isCallback"
})
@Getter
@Setter
@NoArgsConstructor
public class ResponseHeader {

    @JsonProperty("requestId")
    public String requestId;
    @JsonProperty("sendDate")
    public String sendDate;
    @JsonProperty("isCallback")
    public Boolean isCallback;

}