package com.example.schedule2.repository;

import com.example.schedule2.entity.Schedule;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    //CRUD 담당
    Schedule saveSchedule(Schedule schedule); // 일정 저장, Create
    Optional<Schedule> findScheduleById(Long id); // 일정 단건 조회, Get
    List<Schedule> findAllSchedule(); // 일정 전체 조회, Get
    Schedule updateSchedule(Long id, String todo, String user, LocalDateTime updateDate); // 일정 수정
    void deleteSchedule(Long id); // 일정 삭제
}
