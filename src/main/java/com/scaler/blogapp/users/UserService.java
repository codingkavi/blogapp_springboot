package com.scaler.blogapp.users;


import com.scaler.blogapp.users.dtos.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserEntity createUser(CreateUserRequest u) {
        var newUser = UserEntity.builder()
                .username(u.getUsername())
       //       .password(u.getPassword())
                .email(u.getEmail())
                .build();

        return usersRepository.save(newUser);
    }

    public UserEntity getUser(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
    public UserEntity getUser(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity loginUser(String username, String password) {
        var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        //TODO match password
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with username " + username + "not found");
        }

        public UserNotFoundException(Long  userId) {
            super("User with id " + userId + "not found");
        }
    }
}
