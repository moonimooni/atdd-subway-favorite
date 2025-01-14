package nextstep.auth.application.oauth;

import nextstep.auth.application.dto.GithubProfileResponse;
import nextstep.auth.application.dto.GithubTokenRequest;
import nextstep.auth.ui.dto.TokenResponseBody;
import nextstep.auth.ui.dto.TokenFromGithubRequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubClient {
    @Value("${oauth2.github.base-url}")
    private String baseUrl;
    @Value("${oauth2.github.client-id}")
    private String clientId;
    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    public GithubClient() {}

    public String requestToken(TokenFromGithubRequestBody request) {
        String requestTokenPath = "/github/login/oauth/access_token";

        GithubTokenRequest tokenRequest = new GithubTokenRequest(request.getCode(), clientId, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(tokenRequest, headers);

        return new RestTemplate()
                .exchange(baseUrl + requestTokenPath, HttpMethod.POST, httpEntity, TokenResponseBody.class)
                .getBody()
                .getAccessToken();
    }

    public GithubProfileResponse requestProfile(String accessToken) {
        String requestPath = "/github/user";

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(headers);

        return new RestTemplate()
                .exchange(baseUrl + requestPath, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                .getBody();
    }
}
