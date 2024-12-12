package com.service;

import com.model.User;
import com.payload.response.UserSignUpResponse;

public interface LoginService {
    User getActiveUserByEmail(String email);

    UserSignUpResponse login(User user);

    Object logout(String jwt);
}
