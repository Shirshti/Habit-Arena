package com.shrii.habitarena.Controller;

import com.shrii.habitarena.DTO.LoginRequest;
import com.shrii.habitarena.Entity.User;
import com.shrii.habitarena.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody LoginRequest request){
        userService.registerUser(request.getUsername(), request.getPassword());
        return "User registered successfully";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        return userService.loginUser(request.getUsername(), request.getPassword());
    }
}
