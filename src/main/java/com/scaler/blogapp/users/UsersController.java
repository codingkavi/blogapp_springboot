package com.scaler.blogapp.users;


import com.scaler.blogapp.common.dto.ErrorResponse;
import com.scaler.blogapp.security.JWTService;
import com.scaler.blogapp.users.dtos.CreateUserRequest;
import com.scaler.blogapp.users.dtos.LoginUserRequest;
import com.scaler.blogapp.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private JWTService jwtService;

    public UsersController(UserService userService, ModelMapper modelMapper,JWTService jwtService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> signUp(@RequestBody CreateUserRequest request) {
        var savedUser = userService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(
                jwtService.createJwt(savedUser.getId())
        );
        return ResponseEntity.created(savedUserUri).body(userResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request)  {
       UserEntity savedUser = userService.loginUser(request.getUsername(), request.getPassword());
        UserResponse response = modelMapper.map(savedUser, UserResponse.class);
        response.setToken(
                jwtService.createJwt(savedUser.getId())
        );
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({
            UserService.UserNotFoundException.class,
            UserService.InvalidCredentialException.class
    })
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidCredentialException) {
            message = ex.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } else {
            message = "something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }


}
