package com.service;

import com.model.User;
import com.payload.request.UserRequest;

public interface UserService {
    User registerUser(UserRequest userRequest);

    User getUserById(Long id);
}
