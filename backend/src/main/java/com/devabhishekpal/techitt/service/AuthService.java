package com.devabhishekpal.techitt.service;


import com.devabhishekpal.techitt.dto.RegisterRequest;
import com.devabhishekpal.techitt.exceptions.TechittException;
import com.devabhishekpal.techitt.model.NotificationMail;
import com.devabhishekpal.techitt.model.User;
import com.devabhishekpal.techitt.model.VerificationToken;
import com.devabhishekpal.techitt.repository.UserRepo;
import com.devabhishekpal.techitt.repository.VerificationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final MailService mailService;


    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepo.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationMail("Please Activate you Account", user.getEmail(), "Thank you for signing up for Techitt,\n" +
                "We would love to get to know you better, but for that you would need to verify.\n" +
                "Click the link below to activate your account:\n" +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepo.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
        verificationToken.orElseThrow(()-> new TechittException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepo.findByUsername(username).orElseThrow(() -> new TechittException("User not found with name : "+ username));
        user.setEnabled(true);
        userRepo.save(user);
    }
}
