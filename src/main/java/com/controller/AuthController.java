package com.controller;

import com.exception.RecordNotFoundException;
import com.model.User;
import com.payload.request.LoginRequest;
import com.payload.request.UserRequest;
import com.payload.response.MessageResponse;
import com.payload.response.UserSignUpResponse;
import com.repository.UserRepository;
import com.security.TokenProvider;
import com.service.LoginService;
import com.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    LoginService loginService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @ApiOperation(value = "This API will be used for user registration")
    @RequestMapping(value = {"/register-user"}, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) throws Exception {
        try {
            // Step 1: Register the User
            User user = userRepository.findEmailAndIsActive(userRequest.getEmail(),true);
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

    @ApiOperation(value = "This API will be used to login")
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) throws Exception {
        try{
            User user = loginService.getActiveUserByEmail(loginRequest.getEmail());
            if(user != null) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );
                UserSignUpResponse userSignUpResponse = loginService.login(user);
                String token = tokenProvider.createToken(authentication, userSignUpResponse.getRole());
                userSignUpResponse.setAuthToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return ResponseEntity.ok(userSignUpResponse);
            }else {
                throw new RecordNotFoundException("User not found with mobile number:: " + loginRequest.getEmail());
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    @ApiOperation(value = "This API will be used to logout")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request){
        String jwt = tokenProvider.getJwtFromRequest(request);
        return ResponseEntity.ok(loginService.logout(jwt));
    }
}
