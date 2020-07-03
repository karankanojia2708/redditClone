package com.akumaa.reddit.service;

import com.akumaa.reddit.SpringRedditException;
import com.akumaa.reddit.dto.RegisterRequest;
import com.akumaa.reddit.model.NotificationEmail;
import com.akumaa.reddit.model.User;
import com.akumaa.reddit.model.VerificationToken;
import com.akumaa.reddit.repository.UserRepository;
import com.akumaa.reddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnable(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please activate your account",
                user.getEmail(),
                "localhost:8080/api/auth/accountVerification/"+token
        ));

    }

    public void verifyAccount(String token){
        Optional<VerificationToken> optionalVerificationToken =
                verificationTokenRepository.findByToken(token);

        optionalVerificationToken.orElseThrow(()->new SpringRedditException("Invalid token"));
        fetchUserAndEnable(optionalVerificationToken.get());
    }

    public void fetchUserAndEnable(VerificationToken v){
        Long userId = v.getUser().getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new SpringRedditException("User not found"));
        user.setEnable(true);
        userRepository.save(user); 
    }



    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

}
