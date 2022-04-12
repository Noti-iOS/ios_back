package com.example.ios_back.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleDtoV2 {

    private List<TodoDTO> todo = new ArrayList<>();
    private MemoDTO memo;
}