package com.example.ios_back.controller.dto;

import com.example.ios_back.domain.Subject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class HomeworkDTO {
    private Long homeworkId;
    private String name;
    private boolean complete;

    public HomeworkDTO(Long homeworkId, String name, boolean complete) {
        this.homeworkId = homeworkId;
        this.name = name;
        this.complete = complete;
    }
}
