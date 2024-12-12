package com.service;

import com.annotation.Logged;
import com.dao.UserDao;
import com.model.Faculties;
import com.model.User;
import com.payload.response.UserSignUpResponse;
import com.repository.FacultiesRepository;
import com.repository.UserRepository;
import com.security.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    FacultiesRepository facultiesRepository;
    @Autowired
    UserDao userDto;
    @Autowired
    private TokenProvider tokenProvider;
    @Override
    public User getActiveUserByEmail(String email) {
        User res = userRepository.findEmailAndIsActive(email,true);
        return res;
    }
    @Logged
    @Override
    public UserSignUpResponse login(User res) {
        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
        userSignUpResponse.setId(res.getId());
        userSignUpResponse.setFirstName(res.getFirstName());
        userSignUpResponse.setLastName(res.getLastName());
        userSignUpResponse.setEmail(res.getEmail());
        userSignUpResponse.setProfilePic(res.getProfilePic());
        userSignUpResponse.setRole(res.getRole());
        userSignUpResponse.setUserName(res.getUserName());
        userSignUpResponse.setCreatedOn(res.getCreatedOn());
        userSignUpResponse.setUpdatedOn(res.getUpdatedOn());
        res.setIsLoggedIn(true);
        if(res.getRole().equalsIgnoreCase("faculty")){
            Faculties faculties = facultiesRepository.findByUserId(res.getId(),true);
            if(null != faculties){
                userSignUpResponse.setIsVerified(faculties.getIsVerified());
            }
        }
        userRepository.save(res);
        return userSignUpResponse;
    }

    @Override
    public Object logout(String token) {
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token,"student")) {
            Long userId = tokenProvider.getUserIdFromToken(token,"student");
            Integer isLoggedin = userDto.logout(userId);
            if (isLoggedin != 0){
                logger.info("Logout current logged in user");
                return true;
            }
    }
        return false;
    }
}
