package com.example.ios_back.service.Impl;

import com.example.ios_back.domain.Homework;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.domain.Subject;
import com.example.ios_back.repository.HomeworkRepository;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.repository.SubjectRepository;
import com.example.ios_back.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final SubjectRepository subjectRepository;


    @Override
    @Transactional
    public Long addHomework(Long subjectId, String name) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        Subject subject = optionalSubject.orElseThrow(NoSuchElementException::new);
        Homework homework = Homework.createHomework(subject, "p.100-120");

        return homeworkRepository.save(homework).getId();
    }

    @Override
    public boolean modifyComplete(Long homeworkId) {
        Optional<Homework> homeworkOptional = homeworkRepository.findById(homeworkId);
        Homework homework = homeworkOptional.orElseThrow(() -> new NoSuchElementException("해당 homework가 존재하지 않습니다"));
        return homework.changeComplete();
    }
}
