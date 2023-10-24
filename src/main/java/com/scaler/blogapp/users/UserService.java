package com.scaler.blogapp.users;


import com.scaler.blogapp.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest u) {
        var newUser = modelMapper.map(u,UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(u.getPassword()));
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
        var passMatch = passwordEncoder.matches(password,user.getPassword());
        if(!passMatch) {
            throw new InvalidCredentialException();
        }
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

    public static class InvalidCredentialException extends IllegalArgumentException {
        public InvalidCredentialException() {
            super("Invalid username or password combination");
        }
    }
}
