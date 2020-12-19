package com.nuchat.capricorn.service;

import com.nuchat.capricorn.dto.ProfileDTO;
import com.nuchat.capricorn.model.User;
import com.nuchat.capricorn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    public ProfileDTO getProfile(Integer id){
        Optional<User> user = userRepository.findById(id);
        return modelMapper.map(user,ProfileDTO.class);
    }
}
