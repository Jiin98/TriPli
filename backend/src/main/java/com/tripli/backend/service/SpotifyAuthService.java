package com.tripli.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.tripli.backend.dto.SpotifyUserInfo;


@Service
public class SpotifyAuthService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        // 요청 바디 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("redirect_uri", redirectUri);

        // 요청 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // Access Token 요청
        ResponseEntity<String> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            // JSON 응답에서 Access Token 추출
            String[] parts = response.getBody().split("\"access_token\":\"");
            return parts[1].split("\"")[0];
        } else {
            throw new RuntimeException("Failed to retrieve access token: " + response.getStatusCode());
        }
    }

    public SpotifyUserInfo getUserInfo(String accessToken) {
        String url = "https://api.spotify.com/v1/me";
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정 (Bearer 토큰 추가)
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer 인증 설정
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<SpotifyUserInfo> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                SpotifyUserInfo.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // 사용자 정보 반환
        } else {
            throw new RuntimeException("Failed to retrieve user info: " + response.getStatusCode());
        }
    }
}
