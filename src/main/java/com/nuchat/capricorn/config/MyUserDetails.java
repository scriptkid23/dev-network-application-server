package com.nuchat.capricorn.config;

import com.nuchat.capricorn.model.Users;
import com.nuchat.capricorn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Users users = userRepository.findByEmail(email);

        if (users == null) {
            throw new UsernameNotFoundException("User '" + users.getEmail() + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(String.valueOf(users))//
                .password(users.getPassword())//
                .authorities(users.getRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
