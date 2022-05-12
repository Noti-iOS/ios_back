package com.example.ios_back.controller.api;

import com.example.ios_back.domain.Schedule;
import com.example.ios_back.repository.ScheduleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class HomeControllerTest {

    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private HomeController homeController;


    @Test
    void addSchedule_스케쥴_있을때() {
        //given
        LocalDate date = LocalDate.of(2022, 3, 18);
        Schedule schedule = Schedule.createSchedule(date);
        Schedule save = scheduleRepository.save(schedule);
        List<Schedule> before = scheduleRepository.findAll();

        //when
        homeController.addSchedule("2022-03-18");

        //then
        List<Schedule> after = scheduleRepository.findAll();
        Assertions.assertThat(after.size()).isEqualTo(before.size());
    }

    @Test
    void addSchedule_스케쥴_없을때() {
        //given
        List<Schedule> before = scheduleRepository.findAll();

        //when
        homeController.addSchedule("2022-03-18");

        //then
        List<Schedule> after = scheduleRepository.findAll();
        Assertions.assertThat(after.size()).isEqualTo(before.size()+1);
    }

}