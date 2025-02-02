package com.example.schedule2.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id; // 아이디
    private String todo; // 할일
    private String user; // 사용자
    private LocalDateTime createDate; // 작성일
    private LocalDateTime updateDate; // 수정일

    public ScheduleResponseDto(Long id, String todo, String user, LocalDateTime updateDate, LocalDateTime createDate) {
        this.id = id;
        this.todo = todo;
        this.user = user;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }

    public ScheduleResponseDto(Long id, String todo, String user, LocalDateTime updateDate) {
        this.id = id;
        this.todo = todo;
        this.user = user;
        this.updateDate = updateDate;
    }


}
