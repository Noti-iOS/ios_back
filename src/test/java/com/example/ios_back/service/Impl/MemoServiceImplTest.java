package com.example.ios_back.service.Impl;

import com.example.ios_back.domain.Memo;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.repository.MemoRepository;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.service.MemoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemoServiceImplTest {

    @Autowired
    private MemoService memoService;
    @Autowired
    private MemoRepository memoRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void 있던_메모_수정() {
        //given
        LocalDate date = LocalDate.of(2022, 3, 17);
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(date));

        String before = "before";
        memoService.saveMemo(schedule.getId(), before);

        //when
        String after = "after";
        memoService.editMemo(schedule.getId(), after);

        //then
        Memo memo = memoRepository.findBySchedule(schedule).get();
        assertThat(memo.getContent()).isEqualTo(after);
    }

    @Test
    void 없었던_메모_수정() {
        //given
        LocalDate date = LocalDate.of(2022, 3, 17);
        Schedule schedule = scheduleRepository.save(Schedule.createSchedule(date));

        //when
        String content = "content";
        memoService.editMemo(schedule.getId(), content);

        //then
        Memo memo = memoRepository.findBySchedule(schedule).get();
        assertThat(memo.getContent()).isNotNull();
        assertThat(memo.getContent()).isEqualTo(content);
    }

}