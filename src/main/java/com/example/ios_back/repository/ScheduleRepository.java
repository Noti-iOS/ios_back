package com.example.ios_back.repository;

import com.example.ios_back.domain.Schedule;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /*
     * 해당 날짜에 따른 스케쥴 객체 조회
     * */
    Optional<Schedule> findByDate(LocalDate date);

    /*
     * 스케쥴 객체 저장
     * */
    @Override
    <S extends Schedule> S save(S entity);

    /*
     * 해당 월에 따른 스케줄 객체 조회*/
    List<Schedule> findAllByDateLike(String yearMonth);

    @Query(value = "select * from schedule where date like :year_month", nativeQuery = true)
    List<Schedule> findAllByDates(@Param("year_month")String yearMonth);

}
