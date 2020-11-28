package com.nuchat.capricorn.service;

import com.nuchat.capricorn.config.JwtTokenProvider;
import com.nuchat.capricorn.exception.CustomException;
import com.nuchat.capricorn.model.Users;
import com.nuchat.capricorn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    public String signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(Users users) {

        if (!userRepository.existsByEmail(users.getEmail())) {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            userRepository.save(users);
            return jwtTokenProvider.createToken(users.getEmail(), users.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    public Users search(String email) {
        Users users = userRepository.findByEmail(email);
        if (users == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return users;
    }
    public void setIsPresent(Users users, Boolean stat) {
        users.setIs_present(stat);

        userRepository.save(users);
    }
    public Users whoami(HttpServletRequest req) {
        return userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String email) {
        return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getRoles());
    }
}
