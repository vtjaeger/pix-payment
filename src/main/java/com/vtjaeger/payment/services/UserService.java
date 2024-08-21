package com.vtjaeger.payment.services;

import com.vtjaeger.payment.dtos.UserRequest;
import com.vtjaeger.payment.dtos.UserResponse;
import com.vtjaeger.payment.models.User;
import com.vtjaeger.payment.repositories.UserRepository;
import com.vtjaeger.payment.util.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    public UserResponse saveUser(User user) throws MessagingException, UnsupportedEncodingException {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("email already exists");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            User savedUser = userRepository.save(user);
            mailService.sendVerificationEmail(user);

            return new UserResponse(
                    savedUser.getId(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    savedUser.getPassword());
        }
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);
        if(user == null || user.isEnabled()){
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }
}
