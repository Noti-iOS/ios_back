package com.example.ios_back.repository;

import com.example.ios_back.domain.Memo;
import com.example.ios_back.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    /*
     * 메모 저장
     * */
    @Override
    <S extends Memo> S save(S entity);

    /*
     * 메모 조회
     * */
    Optional<Memo> findBySchedule(Schedule schedule);
}
