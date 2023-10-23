package com.scaler.blogapp.users;


import com.scaler.blogapp.users.dtos.CreateUserRequest;
import com.scaler.blogapp.users.dtos.CreateUserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {


    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private final UserService userService;
    private final ModelMapper modelMapper;



    @PostMapping("")
    public ResponseEntity<CreateUserResponse> signUp(@RequestBody CreateUserRequest request) {
        var savedUser = userService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        return ResponseEntity.created(savedUserUri).body(modelMapper.map(savedUser,CreateUserResponse.class));
    }


    @PostMapping("")
    public void loginUser() {

    }
}
