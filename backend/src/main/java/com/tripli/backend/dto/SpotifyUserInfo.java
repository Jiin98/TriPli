package com.tripli.backend.dto;

import java.util.List;

public class SpotifyUserInfo {
    private String id; // Spotify User ID
    private String email; // 사용자 이메일
    private List<Image> images; // 사용자 프로필 이미지

    // Nested class for 이미지 처리
    public static class Image {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
