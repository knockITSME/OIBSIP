package com.obisip.digital_library.controller;

import com.obisip.digital_library.entity.User;
import com.obisip.digital_library.repository.UserRepository;
import com.obisip.digital_library.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(
            UserService userService,
            UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        if ("ADMIN".equals(user.getRole())) {
            throw new RuntimeException(
                    "Admin user cannot be deleted"
            );
        }

        userRepository.deleteById(id);

        return "User deleted successfully";
    }
}