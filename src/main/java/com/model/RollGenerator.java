package com.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roll_generator")
public class RollGenerator {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private String semOrYear;
    private String rollNumber;
    private Boolean isActive = true;
}
