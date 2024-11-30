package com.tripli.backend.controller;

import com.tripli.backend.dto.UsernameUpdateRequest;
import com.tripli.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/username")
    public String updateUsername(@PathVariable Long id, @RequestBody UsernameUpdateRequest usernameUpdateRequest) {
        userService.updateUsername(id, usernameUpdateRequest);
        return "Username updated successfully.";
    }
}
