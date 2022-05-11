package com.example.ios_back.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
@Getter
public class Memo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String content;

    protected Memo() {}

    //==생성 메소드==//
    public static Memo createMemo(Schedule schedule) {
        Memo newMemo = new Memo();
        newMemo.changeSchedule(schedule);
        return newMemo;
    }

    //==연관관계 메서드==//
    public void changeSchedule(Schedule schedule) {
        this.schedule=schedule;
        this.schedule.setMemo(this);
    }


    //==비지니스 로직==//
    // content 수정
    public void changeContent(String content) {
        this.content = content;
    }
}
