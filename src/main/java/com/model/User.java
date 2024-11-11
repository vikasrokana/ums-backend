package com.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String profilePic;
    private String password;
    private String role;
    private String lastLogin;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Boolean isActive= true;

}
