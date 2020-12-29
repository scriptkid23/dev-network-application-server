package com.nuchat.capricorn.service;

import com.nuchat.capricorn.dto.ListFriendDTO;
import com.nuchat.capricorn.dto.ProfileDTO;
import com.nuchat.capricorn.dto.UserContactDTO;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.model.UserContact;
import com.nuchat.capricorn.repository.UserContactRepository;
import com.nuchat.capricorn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    @Autowired
    ModelMapper modelMapper;
    public ProfileDTO getProfile(Integer id){
        Optional<User> user = userRepository.findById(id);
        return modelMapper.map(user,ProfileDTO.class);
    }

    public List<ListFriendDTO> getListFriend(HttpServletRequest req){
        User user = securityService.whoami(req);
        List<ListFriendDTO> listFriend = userContactRepository.getListFriend(user.getId());

        return listFriend;
    }
}
