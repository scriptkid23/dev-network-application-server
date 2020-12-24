package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.dto.CreateConversationDTO;
import com.nuchat.capricorn.dto.ListFriendDTO;
import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.service.MessageService;
import com.nuchat.capricorn.service.SecurityService;
import com.nuchat.capricorn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public void updateProfile(){

    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getProfile(id), HttpStatus.OK);
    }

    // After the user creates a room then the room has been created and the recipient's id is returned
    // then the client side will broadcast a message to / contact /: id via / queue / contacts / for the receiver to receive the message in the contact list.
    @PostMapping("/create/conversation")
    public ResponseEntity<?> createCoversation(HttpServletRequest req, @RequestBody CreateConversationDTO conversationDTO){
        Conversation conversation  = messageService.createConversation(req);
        User receiver = securityService.search(conversationDTO.getReceiver());
        return new ResponseEntity<>(receiver.getId(), HttpStatus.OK);

    }
    @GetMapping("/list/friend")
    public  ResponseEntity<?> getListFriend(HttpServletRequest req){
        return new ResponseEntity<>(userService.getListFriend(req),HttpStatus.OK);
    }
}
