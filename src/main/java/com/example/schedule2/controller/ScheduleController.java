package com.example.schedule2.controller;

import com.example.schedule2.dto.ScheduleRequestDto;
import com.example.schedule2.dto.ScheduleResponseDto;
import com.example.schedule2.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules") // 일정 추가
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto requestDto){
        LocalDateTime currentDateTimeNow = LocalDateTime.now(); // 작성일, 수정일 동일
        return ResponseEntity.ok(scheduleService.saveDto(requestDto, currentDateTimeNow));
    }

    @GetMapping("/shcedules/{id}") // 일정 단건 조회
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){
        return ResponseEntity.ok(scheduleService.getSchedule(id));
    }

    @GetMapping("/schedules") // 일정 전체 조회
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(){
        return ResponseEntity.ok(scheduleService.getSchedules());
    }

    @PutMapping("/shcedules/{id}") // 일정 수정
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @RequestBody ScheduleRequestDto requestDto){
        LocalDateTime updateNow = LocalDateTime.now();

        return ResponseEntity.ok(scheduleService.updateSchedule(id, requestDto, updateNow));
    }

    @DeleteMapping("/shcedules/{id}") // 일정 삭제
    public void deleteSchedule(@PathVariable Long id, @RequestBody String password){
        scheduleService.deleteSchedule(id, password);
    }

}
