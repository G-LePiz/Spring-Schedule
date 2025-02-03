package com.example.schedule2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id; // id 선언
    private String todo; // 할일
    private String user; // 작성자명
    private String password; // 비밀번호
    private LocalDateTime createDate; // 작성일
    private LocalDateTime updateDate; // 수정일

    public Schedule(String todo, String user, String password) {
        this.todo = todo;
        this.user = user;
        this.password = password;
    }

    public Schedule(Long id, String todo, String user, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.todo = todo;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Schedule(String todo, String user, String password, LocalDateTime currentDateTimeNow) {
        this.todo = todo;
        this.user = user;
        this.password = password;
        this.createDate = currentDateTimeNow;
        this.updateDate = currentDateTimeNow;
    }

    public void setId(long l) {

    }
}
