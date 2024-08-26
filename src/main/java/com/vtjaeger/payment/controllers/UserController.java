package com.vtjaeger.payment.controllers;

import com.vtjaeger.payment.dtos.UserRequest;
import com.vtjaeger.payment.dtos.UserResponse;
import com.vtjaeger.payment.models.User;
import com.vtjaeger.payment.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userRequest.toModel();
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){
        List<User> users = userService.getUsers();

        List<UserResponse> responseList = users.stream().map(
                user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword())
        ).collect(Collectors.toList());

        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if(userService.verify(code)){
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/teste")
    public String teste(){
        return "you are logged in";
    }

}
