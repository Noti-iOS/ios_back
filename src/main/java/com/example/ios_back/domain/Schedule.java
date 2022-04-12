package com.example.ios_back.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "schedule")
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "schedule_id")
    private Long id;

    @OneToOne(mappedBy = "schedule")
    private Memo memo;

    @OneToMany(mappedBy = "schedule")
    private List<Subject> subjectList = new ArrayList<>();

    private LocalDate date;

    //==생성 메소드==//
    public static Schedule createSchedule(LocalDate date) {
        Schedule schedule = new Schedule();
        schedule.date = date;
        return schedule;
    }

    //==연관관계 메서드==/
    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    //==비지니스 로직==//
    public boolean isComplete() {

        List<Boolean> check = new ArrayList<>();
        List<Subject> subjectList = this.getSubjectList();
        subjectList.forEach(subject -> subject.getHomeworkList().forEach(homework -> check.add(homework.isComplete())));

        for (Boolean aBoolean : check) if (aBoolean==Boolean.FALSE) return false;
        return true;
    }

}
