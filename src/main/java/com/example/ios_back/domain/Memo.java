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

    //==생성 메소드==//
    // TODO: 2022.02.07. memo-Schedule 연관관계 메서드 추가
    public static Memo createMemo(Schedule schedule) {
        Memo newMemo = new Memo();
        newMemo.schedule = schedule;
        newMemo.schedule.setMemo(newMemo);
        return newMemo;
    }

    //==비지니스 로직==//
    // content 수정
    public void changeContent(String content) {
        this.content = content;
    }
}
