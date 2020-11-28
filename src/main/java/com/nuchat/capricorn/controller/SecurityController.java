package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.dto.*;
import com.nuchat.capricorn.exception.CustomException;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.service.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class SecurityController {

    @Autowired
    SecurityService securityService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequestDTO user) {
            try {
                SigninResponseDTO signinResponseDTO = new SigninResponseDTO(
                        user.getEmail(),
                        securityService.signin(user.getEmail(),user.getPassword()));
                return new ResponseEntity<>(signinResponseDTO, HttpStatus.OK);
            }
            catch (CustomException e){
                return new ResponseEntity<>(new MessageDTO(e.getHttpStatus(),e.getMessage()),HttpStatus.BAD_REQUEST);
            }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO newUser){
        try {
            SignupResponseDTO signupResponseDTO = new SignupResponseDTO();
            signupResponseDTO.setToken(securityService.signup(modelMapper.map(newUser, User.class)));
            signupResponseDTO.setEmail(newUser.getEmail());
            signupResponseDTO.setId(newUser.getId());
            return new ResponseEntity<>(signupResponseDTO, HttpStatus.OK);
        }
        catch (CustomException e){
            return  new ResponseEntity<>(new MessageDTO(e.getHttpStatus(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/me")
    public User getUserDetail(HttpServletRequest req){
        return securityService.whoami(req);
    }
}
