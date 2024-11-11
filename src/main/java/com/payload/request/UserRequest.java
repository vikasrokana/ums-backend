package com.payload.request;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String profilePic;
    private String password;
    private String role;
    private Long courseId;
    private String courseCode;;
}
