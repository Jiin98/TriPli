package com.tripli.backend.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyPlaylistService {

    private static final String PLAYLIST_URL = "https://api.spotify.com/v1/me/playlists";

    public String getUserPlaylists(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer 인증 설정
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                PLAYLIST_URL,
                HttpMethod.GET,
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // 플레이리스트 데이터 반환
        } else {
            throw new RuntimeException("Failed to retrieve playlists: " + response.getStatusCode());
        }
    }

    public String getPlaylistDetails(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId;

        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer 인증 설정
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // 플레이리스트 세부 정보 반환
        } else {
            throw new RuntimeException("Failed to retrieve playlist details: " + response.getStatusCode());
        }
    }
}
