package com.controller;

import com.model.Enrollment;
import com.model.Student;
import com.model.User;
import com.payload.request.StudentRequest;
import com.payload.request.UserRequest;
import com.payload.response.MessageResponse;
import com.repository.UserRepository;
import com.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "This API will be used for user registration")
    @RequestMapping(value = {"/register-user"}, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) throws Exception {
        try {
            // Step 1: Register the User
            User user = userRepository.findByEmail(userRequest.getEmail());
            if(user == null){
                User user1 = userService.registerUser(userRequest);
                return ResponseEntity.ok(new MessageResponse(false, "User registration successful."));
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(true, "This Email is already exist"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

}
