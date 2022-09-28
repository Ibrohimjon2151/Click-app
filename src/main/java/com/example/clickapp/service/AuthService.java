package com.example.clickapp.service;

import com.example.clickapp.entity.User;
import com.example.clickapp.entity.enums.SystemRoleUser;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.LoginDto;
import com.example.clickapp.payload.RegistorDto.RegisterDto;
import com.example.clickapp.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public ApiResponse registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ApiResponse("BUNDAY USER BOR", false);
        }
        User user = new User();

        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setSystemRoleUser(SystemRoleUser.SYSTEM_USER);

        int code = new Random().nextInt(999999);
        user.setEmailCode(String.valueOf(code).substring(0, 4));
        userRepository.save(user);
//        sendSimpleMessage(user.getEmail(), user.getEmailCode());

        return new ApiResponse("User Saqlandi", true, user);
    }


    public void sendSimpleMessage(String sendingEmail, String emailCode) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ibrohimjonyursunov50@gmail.com");
        message.setTo(sendingEmail);
        message.setSubject("Akauntga code jo'natildi");
        message.setText(emailCode);
        javaMailSender.send(message);
    }


    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getEmailCode().equals(emailCode)) {
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("User bazaga qo'shildi", true);
            } else {
                return new ApiResponse("code hato kirtildi", false);
            }
        }
        return new ApiResponse("Bunday user topilmadi", false);
    }
}
