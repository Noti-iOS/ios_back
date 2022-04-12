package com.example.ios_back.service.Impl;

import com.example.ios_back.domain.Memo;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.repository.MemoRepository;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final ScheduleRepository scheduleRepository;

    //TODO: 2022.02.07 매개변수에 대한 null 처리

    @Override
    public Memo findMemo(Long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        Schedule schedule = optionalSchedule.orElseThrow(() -> new NoSuchElementException());

        return schedule.getMemo();
    }

    @Override
    @Transactional
    public void saveMemo(Long scheduleId, String content) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        Schedule schedule = optionalSchedule.orElseThrow(() -> new NoSuchElementException());

        Memo memo = Memo.createMemo(schedule);
        memo.changeContent(content);

        memoRepository.save(memo);
    }
}
