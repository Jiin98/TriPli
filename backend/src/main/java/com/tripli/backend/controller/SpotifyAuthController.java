package com.tripli.backend.controller;

import com.tripli.backend.dto.SpotifyUserInfo;
import com.tripli.backend.entity.User;
import com.tripli.backend.service.SpotifyAuthService;
import com.tripli.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;
    private final UserService userService;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService, UserService userService) {
        this.spotifyAuthService = spotifyAuthService;
        this.userService = userService;
    }

    @GetMapping("/authorize")
    public String authorizeSpotify() {
        // Spotify Authorization URL 생성
        String authorizationUrl = "https://accounts.spotify.com/authorize" +
                "?client_id=" + spotifyAuthService.getClientId() +
                "&response_type=code" +
                "&redirect_uri=" + spotifyAuthService.getRedirectUri() +
                "&scope=user-read-private%20user-read-email";

        return "Redirect to: <a href='" + authorizationUrl + "'>" + authorizationUrl + "</a>";
    }

    @GetMapping("/callback")
    public String handleCallback(@RequestParam("code") String code) {
        try {
            // Access Token 요청
            String accessToken = spotifyAuthService.getAccessToken(code); // 실제 Access Token 요청
            System.out.println("Access Token: " + accessToken); // 디버깅용 출력

            // /me API 호출
            SpotifyUserInfo userInfo = spotifyAuthService.getUserInfo(accessToken);
            System.out.println("Spotify User Info: " + userInfo); // 디버깅용 출력

            // 사용자 정보를 데이터베이스에 저장
            User user = userService.saveUser(userInfo);

            return "User saved successfully with Spotify ID: " + user.getSpotifyId();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}
