package com.tripli.backend.service;

import com.tripli.backend.dto.UsernameUpdateRequest;
import com.tripli.backend.dto.SpotifyUserInfo;
import com.tripli.backend.entity.User;
import com.tripli.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(SpotifyUserInfo userInfo) {
        // 이미 저장된 사용자 확인
        User existingUser = userRepository.findBySpotifyId(userInfo.getId());
        if (existingUser != null) {
            return existingUser; // 기존 사용자 반환
        }

        // 새 사용자 저장
        User user = new User();
        user.setSpotifyId(userInfo.getId());
        user.setUsername(userInfo.getId()); // 초기 값으로 Spotify ID 설정
        if (!userInfo.getImages().isEmpty()) {
            user.setProfileImage(userInfo.getImages().get(0).getUrl());
        }
        user.setCreatedAt(LocalDateTime.now()); // 생성 시간 설정
        return userRepository.save(user);
    }

    public void updateUsername(Long id, UsernameUpdateRequest usernameUpdateRequest) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(usernameUpdateRequest.getUsername());
            userRepository.save(user); // 변경된 값 저장
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}
