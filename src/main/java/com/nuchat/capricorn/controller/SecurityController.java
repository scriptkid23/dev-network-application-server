package com.nuchat.capricorn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class SecurityController {
    @PostMapping("/sigin")
    public void signin(){
//        TODO:
    }

    @PostMapping("/signup")
    public void signup(){

    }
    @GetMapping("/me")
    public void getUserDetail(){

    }
}
