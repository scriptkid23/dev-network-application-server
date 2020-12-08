package com.nuchat.capricorn.controller;

import com.nuchat.capricorn.dto.CreateConversationDTO;
import com.nuchat.capricorn.dto.Message;
import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.service.MessageService;
import com.nuchat.capricorn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    MessageService messageService;
    @Autowired
    SecurityService securityService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @PutMapping("/update")
    public void updateProfile(){
        //TODO:
    }

    // Sau khi người dùng tạo phòng khi đó phòng đã được tạo và trả về id của người nhận
    // khi đó phía client sẽ phát 1 message đến /contact/:id thông qua /queue/contacts/ để receiver nhận được thông báo ở danh sách contact
    @PostMapping("/create/conversation")
    public ResponseEntity<?> createCoversation(HttpServletRequest req,@RequestBody CreateConversationDTO conversationDTO){
        Conversation conversation  = messageService.createConversation(req);
        User reciver = securityService.search(conversationDTO.getReceiver());
        return new ResponseEntity<>(reciver.getId(), HttpStatus.OK);

    }
    // khi đăng nhập vào hệ thống, người dùng sẽ subcribe danh sách liên hệ của họ
    // họ sẽ subscribe /contact/:id với id là mã id của họ
    // bất cứ có cuộc hội thoại từ người sender họ sẽ nhận được
    @MessageMapping("/contact/:id")
    public void handleSubscribeContact(@Payload Message message) throws Exception{
        messagingTemplate.convertAndSendToUser(
                message.getContactid(),
                "/queue/contacts",
                message
        );
    }
}
