package com.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fee_configuration")
public class FeeConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courseId;
    private Double enrollmentFee;

    //add other fee configuration for now focussing on enrollment fee
    //private Double tuitionFee;
}
