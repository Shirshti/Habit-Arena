package com.shrii.habitarena.Service;

import com.shrii.habitarena.Entity.User;
import com.shrii.habitarena.Repository.UserRepo;
import com.shrii.habitarena.Util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public User registerUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepo.save(user);
    }
    public String loginUser(String username, String password) {

        User user = userRepo.findByUsername(username);

        if (user == null) {
            return "User NOt Found";
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            return "Invalid Password";
        }
        String token = jwtUtil.generateToken(username);
        System.out.println("TOKEN" + token);
        return token;

    }
}
