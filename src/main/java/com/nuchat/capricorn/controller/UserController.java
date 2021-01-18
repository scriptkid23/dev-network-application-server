package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.dto.*;
import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.service.MessageService;
import com.nuchat.capricorn.service.SecurityService;
import com.nuchat.capricorn.service.UserService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;
    @Autowired
    SecurityService securityService;
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(HttpServletRequest req, @RequestBody EditProfileRequestDTO payload){
        userService.editProfile(req,payload);
        return new ResponseEntity<>(new MessageDTO(HttpStatus.OK,"Update profile succeeded"),HttpStatus.OK);


    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getProfile(id), HttpStatus.OK);
    }

    // After the user creates a room then the room has been created and the recipient's id is returned
    // then the client side will broadcast a message to / contact /: id via / queue / contacts / for the receiver to receive the message in the contact list.
    @PostMapping("/create/conversation")
    public ResponseEntity<?> createCoversation(HttpServletRequest req, @RequestBody CreateConversationDTO conversationDTO){
        
        List<User> listUser = new ArrayList<>();
        for (String receiver:conversationDTO.getReceiver()) {
            listUser.add(securityService.search(receiver));
        }

        Conversation conversation  = messageService.createConversation(req,listUser);

        return new ResponseEntity<Conversation>(conversation,HttpStatus.OK);

    }
    @GetMapping("/conversation")
    public ResponseEntity<?> getMessageLog(@RequestParam String channelId){
        System.out.println(channelId);
        return new ResponseEntity<>(messageService.getConversationDetail(channelId),HttpStatus.OK);
    }
    @GetMapping("/list/friend")
    public  ResponseEntity<?> getListFriend(HttpServletRequest req){
        return new ResponseEntity<>(userService.getListFriend(req),HttpStatus.OK);
    }
    @PostMapping("/send/message")
    public ResponseEntity<?> sendMessage(HttpServletRequest req, @RequestBody MessageWebSocketDTO messageWebSocketDTO){
        messageService.sendMessage(req,messageWebSocketDTO);
        return new ResponseEntity<>(messageWebSocketDTO,HttpStatus.OK);
    }

    @GetMapping("/check/member")
    public  ResponseEntity<?> checkMember(@RequestParam String channelId){
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getListNotification(HttpServletRequest req){
        return new ResponseEntity<>(messageService.getListNotification(req),HttpStatus.OK);
    }
    @PostMapping("/accept/friend")
    public ResponseEntity<?> acceptFriendRequest(
            HttpServletRequest req,
            @RequestBody AcceptFriendRequestDTO payload){
        messageService.acceptFriendRequest(req,payload);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
    @GetMapping("/message/log")
    public ResponseEntity<?> getListMessageLog(HttpServletRequest req){
        return new ResponseEntity<>(messageService.getListMessageLog(req),HttpStatus.OK);
    }

}
