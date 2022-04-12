package com.example.ios_back.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class TodoDTO {
    private SubjectDTO subject;
    private List<HomeworkDTO> homeworks;

    public TodoDTO(SubjectDTO subjectDTO, List<HomeworkDTO> homeworkDTOS) {
        this.subject = subjectDTO;
        this.homeworks = homeworkDTOS;
    }
}
