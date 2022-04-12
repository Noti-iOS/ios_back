package com.example.ios_back.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class SubjectDTO {
    private Long subjectId;
    private String name;

    public SubjectDTO(Long subjectId, String name) {
        this.subjectId = subjectId;
        this.name = name;
    }
}
