package com.example.ios_back.service.Impl;

import com.example.ios_back.domain.Homework;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.domain.Subject;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.repository.SubjectRepository;
import com.example.ios_back.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Homework> findHomeworks(Long SubjectId) {

        return null;
    }

    @Override
    @Transactional
    //TODO: 2022.02.08. 과목명 같은 경우 처리
    //TODO: 2022.02.08. 매개변수 null 처리
    public Long addSubject(Long scheduleId, String name) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        Schedule schedule = optionalSchedule.orElseThrow(() -> new NoSuchElementException());
        Subject subject = Subject.createSubject(schedule, name);
        Subject save = subjectRepository.save(subject);
        return save.getId();
    }


}
