package com.example.ios_back.repository;

import com.example.ios_back.domain.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    /*
     * 추가 */
    @Override
    <S extends Homework> S save(S entity);

    /*
     * 조회 */
    @Override
    Optional<Homework> findById(Long aLong);
}
