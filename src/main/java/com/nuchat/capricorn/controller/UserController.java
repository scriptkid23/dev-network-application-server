package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @PutMapping("/update")
    public void updateProfile(){

    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getProfile(id), HttpStatus.OK);
    }
}
