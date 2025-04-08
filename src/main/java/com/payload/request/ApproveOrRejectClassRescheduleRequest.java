package com.payload.request;

import lombok.Data;

@Data
public class ApproveOrRejectClassRescheduleRequest {

    private Long classScheduleId;
    private Boolean isRescheduled = true;
    private Boolean isApproved = null;
}
