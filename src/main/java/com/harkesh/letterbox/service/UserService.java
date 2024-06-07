package com.harkesh.letterbox.service;

import com.harkesh.letterbox.dto.UserDto;
import com.harkesh.letterbox.entity.User;
import com.harkesh.letterbox.exceptions.AlreadyExistException;
import com.harkesh.letterbox.exceptions.InvalidUserRequest;
import com.harkesh.letterbox.exceptions.NotFoundException;
import com.harkesh.letterbox.repository.RoleRepository;
import com.harkesh.letterbox.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public long createUser(UserDto userDto) {
        User user = new User();

        user.setName(userDto.getName());

        if (!Objects.isNull(userDto.getPassword()) && !(userDto.getPassword().length() < 6)) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            throw new InvalidUserRequest("Password not valid");
        }

        // check for duplicate email
//        if (!Objects.isNull(userDto.getEmail()) && !userDto.getEmail().isEmpty()) {
//            if (this.isDuplicateEmail(user.getEmail(), userDto.getId())) {
//                throw new AlreadyExistException("User with email: " + userDto.getEmail() + " already exist.");
//            }
//
            user.setEmail(userDto.getEmail());
//        }

        // check for duplicate username
//        if (!Objects.isNull(userDto.getUsername()) && !userDto.getUsername().isEmpty()) {
//            if (this.isDuplicateUsername(user.getUsername(), userDto.getId())) {
//                throw new AlreadyExistException("User with username: " + userDto.getUsername() + " already exist.");
//            }

            user.setUsername(userDto.getUsername());
//        }

        user.setRoles(userDto.getRoles());

        user = userRepository.save(user);
        return user.getId();
    }


    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new NotFoundException("User Not Found")
        );

        return convertUserToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new NotFoundException("No User Found!");
        }

        return users.stream()
            .map(this::convertUserToUserDto)
            .collect(Collectors.toList());
    }

    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(
            () -> new NotFoundException("No User Found with id: " + userDto.getId())
        );

        // check for duplicate email
        if (!Objects.isNull(userDto.getEmail()) && !userDto.getEmail().isEmpty()) {
            if (this.isDuplicateEmail(user.getEmail(), userDto.getId())) {
                throw new AlreadyExistException("User with email: " + userDto.getEmail() + " already exist.");
            }

            user.setEmail(userDto.getEmail());
        }

        // check for duplicate username
        if (!Objects.isNull(userDto.getUsername()) && !userDto.getUsername().isEmpty()) {
            if (this.isDuplicateUsername(user.getUsername(), userDto.getId())) {
                throw new AlreadyExistException("User with username: " + userDto.getUsername() + " already exist.");
            }

            user.setUsername(userDto.getUsername());
        }

        if (!Objects.isNull(userDto.getPassword()) && !(userDto.getPassword().length() < 6)) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(user);
    }

    private UserDto convertUserToUserDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .username(user.getEmail())
            .roles(user.getRoles())
            .build();
    }

    private boolean isDuplicateEmail(String email, long id) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent() && !Objects.equals(optionalUser.get().getId(), id);
    }

    private boolean isDuplicateUsername(String username, long id) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.isPresent() && !Objects.equals(optionalUser.get().getId(), id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("No username exists with username: " + username)
        );
    }
}
