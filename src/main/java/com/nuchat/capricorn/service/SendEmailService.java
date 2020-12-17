package com.nuchat.capricorn.service;

import com.nuchat.capricorn.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender emailSender;

    private static final Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    public static int noOfQuickServiceThreads = 20;
    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    public MessageDTO sendEmail(String email, String token) throws MessagingException, RuntimeException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
//
        message.setFrom("Nuchat Service <nuchat.spirity@gmail.com>");
        message.setTo(email);
        message.setSubject("Forgot Password");

        String link = "https://nuzchat.herokuapp.com/auth/?token="+token;
        message.setText("If you've lost your password or wish to reset it, use the link to get started: "+link);
        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    emailSender.send(mimeMessage);
                }catch(Exception e){
                    logger.error("Exception occur while send a mail : ",e);
                }
            }
        });


        return  new MessageDTO(HttpStatus.OK,"Please Check email!");
    }
}
