package com.example.ios_back.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ScheduleDTO3 {
    private Long scheduleId;
    private LocalDate date;
    private boolean complete;

    public ScheduleDTO3(Long scheduleId, LocalDate date, boolean complete) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.complete = complete;
    }
}
