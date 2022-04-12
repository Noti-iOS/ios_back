package com.example.ios_back.service;

import com.example.ios_back.domain.Memo;

public interface MemoService {
    /*
     * 메모 수정
     * */

    /*
     * 메모 조회
     * */
    public Memo findMemo(Long scheduleId);

    /*
     * 메모 저장
     * */
    public void saveMemo(Long scheduleId, String content);



}
