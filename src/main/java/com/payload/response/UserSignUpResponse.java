package com.payload.response;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class UserSignUpResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String profilePic;
    private String password;
    private String role;
    private String lastLogin;
    private Boolean isLoggedIn;
    private Boolean isVerified;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private String authToken;
}
