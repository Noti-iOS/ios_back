package com.example.ios_back.service.Impl;

import com.example.ios_back.domain.Memo;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.domain.Subject;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // TODO: 2021.02.07. DTO 로 넘기기
    // TODO: 2021.02.07. null 처리(예외처리)
    @Override
    public Schedule findSchedule(LocalDate localDate) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findByDate(localDate);
        return optionalSchedule.orElseThrow(NoSuchElementException::new);
    }

    /**
     * 이 메소드는 localDate를 가지는 인스턴스를 찾고 없다면 생성한다.
     * @param localDate
     * @return
     */
    @Override
    public Schedule getSchedule(LocalDate localDate) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findByDate(localDate);
        return optionalSchedule
                .orElse(scheduleRepository.save(optionalSchedule.orElseGet(() -> Schedule.createSchedule(localDate))));
    }

    @Override
    public List<Subject> findSubjects(Long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        Schedule schedule = optionalSchedule.orElseThrow(NoSuchElementException::new);
        return schedule.getSubjectList();
    }


    @Override
    @Transactional
    public void storeSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }


    @Override
    public List<Schedule> findAllScheduleByMonth(String year, String month) {
        return scheduleRepository.findAllByDates(year + "-" + month + "-%");
    }
}
