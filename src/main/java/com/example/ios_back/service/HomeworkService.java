package com.example.ios_back.service;

public interface HomeworkService {
    /*
     * 숙제 추가*/
    public Long addHomework(Long subjectId, String name);

    /*
     * 완료 수정*/
    public boolean modifyComplete(Long subjectId);


}
