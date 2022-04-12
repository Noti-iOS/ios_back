package com.example.ios_back.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
@Getter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "subject_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "subject")
    private List<Homework> homeworkList = new ArrayList<>();

    //==생성 메소드==//
    public static Subject createSubject(Schedule schedule, String name) {
        Subject newSubject = new Subject();
        newSubject.schedule = schedule;
        newSubject.schedule.getSubjectList().add(newSubject);
        newSubject.name = name;
        return newSubject;
    }

    //==연관관계 메서드==//

}
