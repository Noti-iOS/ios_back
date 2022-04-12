package com.example.ios_back.service.Impl;

import com.example.ios_back.domain.Schedule;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ScheduleServiceImplTest {

    @Autowired ScheduleService scheduleService;
    @Autowired ScheduleRepository scheduleRepository;

    //TODO: 롤백 문제 해결
    @Test
    @DisplayName("년도와 월을 주면 그에 해당하는 객체리스트 반환")
    public void findAllScheduleByMonthTest() {

        LocalDate date1 = LocalDate.of(2022, 3, 17);
        LocalDate date2 = LocalDate.of(2022, 3, 18);
        LocalDate date3 = LocalDate.of(2022, 3, 19);
        Schedule schedule1 = Schedule.createSchedule(date1);
        Schedule schedule2 = Schedule.createSchedule(date2);
        Schedule schedule3 = Schedule.createSchedule(date3);

        scheduleService.storeSchedule(schedule1);
        scheduleService.storeSchedule(schedule2);
        scheduleService.storeSchedule(schedule3);

        List<Schedule> allScheduleByMonth = scheduleService.findAllScheduleByMonth("2022", "03");

//        assertThat(allScheduleByMonth.size()).isEqualTo(3);

    }

}