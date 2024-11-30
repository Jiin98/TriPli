package com.tripli.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String query;

    @Column(nullable = false, updatable = false)
    private LocalDateTime searchedAt;

    public SearchHistory() {
        this.searchedAt = LocalDateTime.now();
    }
}
