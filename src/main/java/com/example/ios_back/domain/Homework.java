package com.example.ios_back.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "homework")
@Getter
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "homework")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String name;
    private boolean complete;

    //==생성 메서드==//
    public static Homework createHomework(Subject subject, String name) {
        Homework newHomework = new Homework();
        newHomework.setSubject(subject);
        newHomework.complete = false;
        newHomework.name = name;
        return newHomework;
    }

    //==연관관계 메서드==//
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.getHomeworkList().add(this);
    }

    //==비지니스 로직==//

    public boolean changeComplete() {
        return !this.complete;
    }
}
