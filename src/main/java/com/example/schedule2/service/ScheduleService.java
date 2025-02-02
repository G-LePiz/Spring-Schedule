package com.example.schedule2.service;

import com.example.schedule2.dto.ScheduleRequestDto;
import com.example.schedule2.dto.ScheduleResponseDto;
import com.example.schedule2.entity.Schedule;
import com.example.schedule2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto saveDto(ScheduleRequestDto requestDto, LocalDateTime currentDateTimeNow){ // 일정 생성
        Schedule schedule = new Schedule(requestDto.getTodo(), requestDto.getUser(), requestDto.getPassword(), currentDateTimeNow);
        Schedule saveSchedule = scheduleRepository.saveSchedule(schedule);
        return new ScheduleResponseDto(saveSchedule.getId(), saveSchedule.getTodo(), saveSchedule.getUser(), saveSchedule.getCreateDate(), saveSchedule.getUpdateDate());
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id){ // 단건 일정 조회
        Schedule schedule = scheduleRepository.findScheduleById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 일정이 없습니다.")
        );
        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser(), schedule.getCreateDate(), schedule.getUpdateDate());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules(){ // 전체 일정 조회
        List<Schedule> scheduleList = scheduleRepository.findAllSchedule();

        List<ScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();
        for(Schedule schedule : scheduleList){
            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser(), schedule.getCreateDate(), schedule.getUpdateDate());
            scheduleResponseDtoList.add(scheduleResponseDto);
        }
        return scheduleResponseDtoList;
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto, LocalDateTime updateDate) { // 일정 수정
        Schedule scheduleById = scheduleRepository.findScheduleById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 일정이 없습니다."));
        if (!scheduleRequestDto.getPassword().equals(scheduleById.getPassword())){
            throw new RuntimeException();
        }
        Schedule schedule = scheduleRepository.updateSchedule(id, scheduleRequestDto.getTodo(), scheduleRequestDto.getUser(), updateDate);
        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser(), schedule.getUpdateDate());
    }

    @Transactional
    public void deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto){ // 일정 삭제
        Schedule scheduleById = scheduleRepository.findScheduleById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 일정이 없습니다."));
        if (!scheduleRequestDto.getPassword().equals(scheduleById.getPassword())){ // 비번이 맞지않으면 예외처리
            throw new RuntimeException();
        }
        scheduleRepository.deleteSchedule(id);
    }
}
