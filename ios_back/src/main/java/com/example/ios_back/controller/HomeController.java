package com.example.ios_back.controller;

import com.example.ios_back.controller.dto.*;
import com.example.ios_back.controller.form.CreateHomeworkForm;
import com.example.ios_back.controller.form.CreateMemoForm;
import com.example.ios_back.controller.form.CreateSubjectForm;
import com.example.ios_back.domain.Homework;
import com.example.ios_back.domain.Schedule;
import com.example.ios_back.domain.Subject;
import com.example.ios_back.repository.ScheduleRepository;
import com.example.ios_back.repository.SubjectRepository;
import com.example.ios_back.service.HomeworkService;
import com.example.ios_back.service.MemoService;
import com.example.ios_back.service.ScheduleService;
import com.example.ios_back.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class HomeController {

    private final ScheduleService scheduleService;
    private final SubjectService subjectService;
    private final HomeworkService homeworkService;
    private final ScheduleRepository scheduleRepository;
    private final MemoService memoService;
    private final SubjectRepository subjectRepository;

    /*
     * 일정 조회 */
    @GetMapping("/schedule/date")
    //TODO: 2022.02.08. memo, subject, homework 예외처리(null)
    public ScheduleDTO getSchedule(@RequestParam String date) {//2017-11-21
        LocalDate requestDate = LocalDate.parse(date);
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        Schedule schedule = scheduleService.findSchedule(requestDate);
        List<Subject> subjectList = schedule.getSubjectList();

        for (Subject subject : subjectList) {
            List<HomeworkDTO> homeworkDTOList = subject.getHomeworkList().stream().map(homework ->
                    new HomeworkDTO(homework.getId(), homework.getName(), homework.isComplete())
            ).collect(Collectors.toList());
            scheduleDTO.getMap().put(subject.getName(), homeworkDTOList);
        }

        scheduleDTO.setMemo(new MemoDTO(schedule.getMemo().getId(), schedule.getMemo().getContent()));

        return scheduleDTO;
    }

    @GetMapping("/schedule/v2/date")
    public ScheduleDtoV2 getSchedule_V2(@RequestParam String date) {//2017-11-21
        LocalDate requestDate = LocalDate.parse(date);
        ScheduleDtoV2 scheduleDtoV2 = new ScheduleDtoV2();

//        ScheduleDTO scheduleDTO = new ScheduleDTO();

        Schedule schedule = scheduleService.findSchedule(requestDate);
        List<Subject> subjectList = schedule.getSubjectList();

        for (Subject subject : subjectList) {
            List<HomeworkDTO> homeworkDTOList = subject.getHomeworkList().stream()
                    .map(homework -> new HomeworkDTO(homework.getId(), homework.getName(), homework.isComplete()))
                    .collect(Collectors.toList());
            scheduleDtoV2.getTodo().add(new TodoDTO(new SubjectDTO(subject.getId(),subject.getName()), homeworkDTOList));
//            scheduleDTO.getMap().put(subject.getName(), homeworkDTOList);
        }

        scheduleDtoV2.setMemoDTO(new MemoDTO(schedule.getMemo().getId(), schedule.getMemo().getContent()));

        return scheduleDtoV2;
    }

    /*
     * 일정 추가*/
    //TODO: 2022.02.08. 과목 null 이면 생성X
    @GetMapping("/schedule/date/new")
    public void addSchedule(@RequestParam String date) {
        LocalDate requestDate = LocalDate.parse(date);
        Schedule newSchedule = Schedule.createSchedule(requestDate);
        scheduleService.storeSchedule(newSchedule);
    }

    /*
     * 과목 추가*/
    @PostMapping("/schedule/subject/new")
    public void addSubject(@RequestBody CreateSubjectForm form) {
        Schedule schedule = scheduleService.findSchedule(LocalDate.parse(form.getDate()));
        subjectService.addSubject(schedule.getId(), form.getSubjectName());
    }

    /*
     * 메모 추가*/
    //TODO: 2022.02.08. 중복 처리
    @PostMapping("/schedule/memo/new")
    public void addMemo(@RequestBody CreateMemoForm form) {
        Schedule schedule = scheduleService.findSchedule(LocalDate.parse(form.getDate()));
        memoService.saveMemo(schedule.getId(), form.getContent());
    }

    /*
     * 숙제 추가*/
    @PostMapping("/schedule/homework/new")
    public void addHomework(@RequestBody CreateHomeworkForm form) {
        Schedule schedule = scheduleService.findSchedule(LocalDate.parse(form.getDate()));
        //TODO: 2022.02.09. service로 옮기기
        Optional<Subject> byScheduleAndName = subjectRepository.findByScheduleAndName(schedule, form.getSubjectName());
        Subject subject = byScheduleAndName.orElseThrow(() -> new NoSuchElementException(""));
        homeworkService.addHomework(subject.getId(), form.getContent());
    }

    /*
     * 숙제 완료 체크 */
    @GetMapping("/schedule/homework/complete")
    public void changeHomework(@RequestParam Long homeworkId) {
        homeworkService.modifyComplete(homeworkId);
    }

    /*
     * 일정 생성 - 과목추가, 메모추가 할 때 */
    public boolean isEmptySchedule(LocalDate localDate) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findByDate(localDate);
        boolean isEmpty = optionalSchedule.isEmpty();
        return isEmpty;
    }

}
