package com.tripli.backend.repository;

import com.tripli.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findBySpotifyId(String spotifyId);
}
