package com.payload.request;

import lombok.Data;

@Data
public class RollNumberRequest {
    private Long id;
    private Long courseId;
    private String rollNumber;
    private Long semOrYear;
}
