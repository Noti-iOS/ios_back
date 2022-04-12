package com.example.ios_back.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoDTO {
    private Long memoId;
    private String content;

    public MemoDTO(Long memoId, String content) {
        this.memoId = memoId;
        this.content = content;
    }
}
