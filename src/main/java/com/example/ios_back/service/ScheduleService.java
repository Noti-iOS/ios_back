package com.example.ios_back.service;

import com.example.ios_back.domain.Memo;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.domain.Subject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    /*
     * Schedule 객체 조회
     * */
    public Schedule findSchedule(LocalDate localDate);

    /*
     * Schedule 객체에 연결된 과목 조회
     * */
    public List<Subject> findSubjects(Long scheduleId);

    /*
     * Schedule 객체 추가
     * */
    public void storeSchedule(Schedule schedule);


    /*
     * Schedule 객체 조회 - 년도-월
     */
    public List<Schedule> findAllScheduleByMonth(String year, String month);

    /**
     * 있으면 반환, 없으면 새로 생성
     * @param localDate
     * @return
     */
    public Schedule getSchedule(LocalDate localDate);
}
