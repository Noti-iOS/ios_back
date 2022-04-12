package com.example.ios_back.controller.dto;

import com.example.ios_back.domain.Homework;
import com.example.ios_back.domain.Memo;
import com.example.ios_back.domain.Subject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ScheduleDTO {

    private Map<String, List<HomeworkDTO>> map = new HashMap<>();
    private MemoDTO memo;

}
