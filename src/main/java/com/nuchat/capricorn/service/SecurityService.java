package com.nuchat.capricorn.service;

import com.nuchat.capricorn.config.JwtTokenProvider;
import com.nuchat.capricorn.config.WebSocketAuthInfo;
import com.nuchat.capricorn.config.WebSocketConfig;
import com.nuchat.capricorn.dto.ResetPasswordRequestDTO;
import com.nuchat.capricorn.exception.CustomException;
import com.nuchat.capricorn.model.Contacts;
import com.nuchat.capricorn.model.RevokeToken;
import com.nuchat.capricorn.model.Role;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.repository.ContactsRepository;
import com.nuchat.capricorn.repository.RevokeTokenRepository;
import com.nuchat.capricorn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SecurityService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactsRepository contactsRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RevokeTokenRepository revokeTokenRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    SendEmailService sendEmailService;
    @Autowired
    private AuthenticationManager authenticationManager;



    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    public String signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(User user) {

        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setAvatar("https://storage-3t.herokuapp.com/uploads/avatar/016-unicorn.svg");
            List<Role> roles = new ArrayList<Role>();
            user.setCreate_at_(new Date());
            roles.add(Role.ROLE_MEMBER);user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Contacts contacts = modelMapper.map(user,Contacts.class);
            contactsRepository.save(contacts);
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    public User search(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }
    public void setIsPresent(User user, Boolean stat) {
        user.setIs_present(stat);

        userRepository.save(user);
    }
    public User whoami(HttpServletRequest req) {
        logger.info(jwtTokenProvider.resolveToken(req));
        return userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String email) {
        return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getRoles());
    }
    public boolean validateToken(String token){
        return jwtTokenProvider.validateToken(token);
    }
    public void revokeToken(HttpServletRequest req){
        RevokeToken revokeToken = new RevokeToken();
        revokeToken.setToken(jwtTokenProvider.resolveToken(req));
        revokeTokenRepository.save(revokeToken);
    }
    public void resetPassword(HttpServletRequest req, ResetPasswordRequestDTO payload){

        if(!payload.getPassword().equals(payload.getCfpassword())){
            throw new CustomException("Password and Confirm Password not match",HttpStatus.BAD_REQUEST);
        }
        User user = whoami(req);
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);
    }

}
