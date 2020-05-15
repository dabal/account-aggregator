package pl.dabal.accountaggregator.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.Consent;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.json.AliorOpenApiRequest;
import pl.dabal.accountaggregator.model.json.PrivilegeList;
import pl.dabal.accountaggregator.model.json.RequestHeader;
import pl.dabal.accountaggregator.model.json.ScopeDetails;

import java.util.List;

@Service
public interface AliorOpenApiService {

    AliorOpenApiRequest buildOpenApiAuthAuthorizeRequestBody(Consent consent);

    RequestHeader buildRequestHeader(Consent consent);

    List<PrivilegeList> buildPrivilegeList();

    ScopeDetails buildScopeDetails(Consent consent);

    String createConsent(User user);

    AliorOpenApiRequest buildOpenApiAuthTokenRequestBody(Consent consent, String requestId, String code);

    String authorizeConsent(String authCode, String state);


}
