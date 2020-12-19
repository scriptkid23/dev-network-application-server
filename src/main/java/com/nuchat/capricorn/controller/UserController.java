package com.nuchat.capricorn.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @PutMapping("/update")
    public void updateProfile(){
        //TODO:update profile of user
    }
    @GetMapping("/{id}")
    public void getUserInformation(@PathVariable Integer id){
        //TODO: get information of user
    }
    @PostMapping("/create/room")
    public void createRoom(){
        //TODO: Create room by owner
    }
    @PostMapping("/send/request/friend")
    public void sendRequestFriend(){
        //TODO: Send request add friend from owner via list member
    }
    @DeleteMapping("unsend/request/friend")
    public void unsendRequestFriend(){
        //TODO: Unsend request after user send request add friend
    }
    @PostMapping("/send/message")
    public void sendMessage(){
        //TODO: send message to room
    }
    



}
