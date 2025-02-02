package com.example.schedule2.repository;

import com.example.schedule2.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Schedule saveSchedule(Schedule schedule) { // 일정 추가
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into schedule(todo, user, password, CreateDate, UpdateDate) values(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, schedule.getTodo());
            ps.setString(2, schedule.getUser());
            ps.setString(3, schedule.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(schedule.getCreateDate()));
            ps.setTimestamp(5, Timestamp.valueOf(schedule.getUpdateDate()));
            return ps;
        }, keyHolder);

        schedule.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return schedule;
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) { // 일정 단건 조회
        List<Schedule> schedules = jdbcTemplate.query(
                "select * from calendar where id = ?",
                (rs, rowNum) -> new Schedule(rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("user"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()),
                id
        );

        return schedules.stream().findAny();
    }

    @Override
    public List<Schedule> findAllSchedule() { // 일정 전체 조회
        return jdbcTemplate.query(
                "select * from calendar",
                (rs, rowNum) ->
                        new Schedule(rs.getLong("id"),
                                rs.getString("todo"),
                                rs.getString("user"),
                                rs.getTimestamp("createDate").toLocalDateTime(),
                                rs.getTimestamp("updateDate").toLocalDateTime())
        );
    }


    @Override
    public Schedule updateSchedule(Long id, String todo, String user, LocalDateTime updateDate) { // 일정 수정

        jdbcTemplate.update(
                "update calendar set todo = ?, user = ?, updateDate = ?, where id = ?",
                todo,
                user,
                updateDate,
                id
        );

        return jdbcTemplate.queryForObject(
                "select * from calendar where id = ?",
                (rs, rowNum) -> new Schedule(rs.getLong("id"), rs.getString("todo"), rs.getString("user"), rs.getTimestamp("createDate").toLocalDateTime(), rs.getTimestamp("updateDate").toLocalDateTime()),
                id
        );
    }

    @Override
    public void deleteSchedule(Long id) { // 일정 삭제
        jdbcTemplate.update(
                "delete from schedule where id = ?",
                id
                //requestdto와 레포지토리에 있는게 같아야함.
        );

    }


}
