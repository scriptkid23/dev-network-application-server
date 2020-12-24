package com.nuchat.capricorn.service;

import com.nuchat.capricorn.dto.ProfileDTO;
import com.nuchat.capricorn.dto.UserContactDTO;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.model.UserContact;
import com.nuchat.capricorn.repository.UserContactRepository;
import com.nuchat.capricorn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserContactRepository userContactRepository;

    @Autowired
    ModelMapper modelMapper;
    public ProfileDTO getProfile(Integer id){
        Optional<User> user = userRepository.findById(id);
        return modelMapper.map(user,ProfileDTO.class);
    }

    public UserContactDTO getListFriend(HttpServletRequest req){

        return null;
    }
}
