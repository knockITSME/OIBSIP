package com.obisip.digital_library.service;

import com.obisip.digital_library.entity.User;
import com.obisip.digital_library.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Every self-registered account must be a normal USER.
        user.setRole("USER");

        return userRepository.save(user);
    }
}