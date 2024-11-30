package com.tripli.backend.controller;

import com.tripli.backend.service.SpotifyPlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyPlaylistController {

    private final SpotifyPlaylistService spotifyPlaylistService;

    public SpotifyPlaylistController(SpotifyPlaylistService spotifyPlaylistService) {
        this.spotifyPlaylistService = spotifyPlaylistService;
    }

    @GetMapping("/playlists")
    public String getUserPlaylists(@RequestParam("accessToken") String accessToken) {
        return spotifyPlaylistService.getUserPlaylists(accessToken);
    }

    @GetMapping("/playlists/details")
    public String getPlaylistDetails(@RequestParam("accessToken") String accessToken,
                                     @RequestParam("playlistId") String playlistId) {
        return spotifyPlaylistService.getPlaylistDetails(accessToken, playlistId);
    }
}
