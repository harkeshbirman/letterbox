package com.harkesh.letterbox.controller;

import com.harkesh.letterbox.dto.UserDto;
import com.harkesh.letterbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public long createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }
}
