package com.nuchat.capricorn.service;

import com.nuchat.capricorn.dto.EditProfileRequestDTO;
import com.nuchat.capricorn.dto.ListFriendDTO;
import com.nuchat.capricorn.dto.ProfileDTO;
import com.nuchat.capricorn.dto.UserContactDTO;
import com.nuchat.capricorn.model.Contacts;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.model.UserContact;
import com.nuchat.capricorn.repository.ContactsRepository;
import com.nuchat.capricorn.repository.UserContactRepository;
import com.nuchat.capricorn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    ContactsRepository contactsRepository;

    @Autowired
    SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    ModelMapper modelMapper;
    public ProfileDTO getProfile(Integer id){

        Optional<User> user = userRepository.findById(id);
        logger.info(id.toString());
        logger.info(user.get().getFirst_name());
        return modelMapper.map(user.get(),ProfileDTO.class);
    }

    public List<ListFriendDTO> getListFriend(HttpServletRequest req){
        User user = securityService.whoami(req);
        List<ListFriendDTO> listFriend = userContactRepository.getListFriend(user.getId());

        return listFriend;
    }
    public void editProfile(HttpServletRequest req, EditProfileRequestDTO payload){
        User user = securityService.whoami(req);
        user.setFirst_name(payload.getFirst_name());
        user.setLast_name(payload.getLast_name());
        user.setBio(payload.getBio());
        user.setAvatar(payload.getAvatar());
        user.setPhone_number(payload.getPhone_number());
        user.setUpdate_at_(new Date());
        userRepository.save(user);
        Contacts contacts = contactsRepository.findByEmail(user.getEmail());
        contacts.setLast_name(payload.getLast_name());
        contacts.setFirst_name(payload.getFirst_name());
        contacts.setAvatar(payload.getAvatar());
        contacts.setPhone(payload.getPhone_number());
        contactsRepository.save(contacts);
    }
}
