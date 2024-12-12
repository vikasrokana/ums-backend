package com.security;

import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {
    static Logger logger = Logger.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndIsActive(email, true).orElseThrow (() -> new UsernameNotFoundException("User not found with mobileNumber : " + email));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userService.getUserById(id);
        UserDetails userPrincipal = null;
        //Only for active users.
        if(user.getIsActive().equals(true)){
            userPrincipal = UserPrincipal.create(user);
        }
        return userPrincipal;
    }
}
