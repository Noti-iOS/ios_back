package com.example.ios_back.controller.api;

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
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class HomeController {

    //TODO: 파라미터 통일
    //TODO: id값 받는 api 없애기

    private final ScheduleService scheduleService;
    private final SubjectService subjectService;
    private final HomeworkService homeworkService;
    private final ScheduleRepository scheduleRepository;
    private final MemoService memoService;
    private final SubjectRepository subjectRepository;


    @GetMapping("/schedule/date")
    public ScheduleDtoV2 getSchedule(@RequestParam(name = "year") String year, @RequestParam(name = "month") String month, @RequestParam(name = "day") String day) {//2017-11-21
        LocalDate requestDate = LocalDate.parse(year+"-"+month+"-"+day);
        ScheduleDtoV2 scheduleDtoV2 = new ScheduleDtoV2();

//        ScheduleDTO scheduleDTO = new ScheduleDTO();

        Schedule schedule = scheduleService.findSchedule(requestDate);
        List<Subject> subjectList = schedule.getSubjectList();

        for (Subject subject : subjectList) {
            List<HomeworkDTO> homeworkDTOList = subject.getHomeworkList().stream()
                    .map(homework -> new HomeworkDTO(homework.getId(), homework.getName(), homework.isComplete()))
                    .collect(Collectors.toList());
            scheduleDtoV2.getTodo().add(new TodoDTO(new SubjectDTO(subject.getId(), subject.getName()), homeworkDTOList));
//            scheduleDTO.getMap().put(subject.getName(), homeworkDTOList);
        }

        scheduleDtoV2.setMemo(new MemoDTO(schedule.getMemo().getId(), schedule.getMemo().getContent()));

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


    /**
     * 과목 추가
     * @param form
     */
    @PostMapping("/schedule/subject/new")
    public void addSubject(@RequestBody CreateSubjectForm form) {
        Schedule schedule = scheduleService.getSchedule(LocalDate.parse(form.getDate()));
        subjectService.addSubject(schedule.getId(), form.getSubjectName());
    }

    /*
     * 메모 추가*/
    //TODO: 2022.02.08. 중복 처리
    @PostMapping("/schedule/memo/new")
    public void addMemo(@RequestBody CreateMemoForm form) {
        Schedule schedule = scheduleService.getSchedule(LocalDate.parse(form.getDate()));
        memoService.saveMemo(schedule.getId(), form.getContent());
    }

    //TODO: 수정 API url 바꾸기
    /**
     * 메모 수정
     * @param form
     */
    @PostMapping("/schedule/memo/edit}")
    public void editMemo(@RequestBody CreateMemoForm form) {
        Schedule schedule = scheduleService.getSchedule(LocalDate.parse(form.getDate()));
        memoService.editMemo(schedule.getId(), form.getContent());
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
     * 캘린더 조회 */
    @GetMapping("/schedule/month")
    public List<ScheduleDTO3> getAllSchedule(String year, String month) {

        List<ScheduleDTO3> scheduleDTO3List = new ArrayList<>();
        List<Schedule> findSchedule = scheduleService.findAllScheduleByMonth(year, month);
        findSchedule.forEach(schedule -> {
            scheduleDTO3List.add(new ScheduleDTO3(schedule.getId(), schedule.getDate(), schedule.isComplete()));

        });

        return scheduleDTO3List;
    }
}
