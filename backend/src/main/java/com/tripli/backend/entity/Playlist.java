package com.tripli.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // TEXT 타입
    private String imageUrl;

    @Column(unique = true)
    private String spotifyId;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0") // INT DEFAULT 0
    private int likesCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Playlist() {
        this.createdAt = LocalDateTime.now();
    }
}
