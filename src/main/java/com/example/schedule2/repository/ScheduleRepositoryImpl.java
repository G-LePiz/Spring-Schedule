package com.example.schedule2.repository;

import ch.qos.logback.core.util.StringUtil;
import com.example.schedule2.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SimpleTimeZone;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Schedule saveSchedule(Schedule schedule) { // 일정 추가
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into calendar(todo, user, password, CreateDate, UpdateDate) values(?, ?, ?, ?, ?)",
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
                        rs.getString("password"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()),
                id
        );

        return schedules.stream().findAny();
    }

    @Override
    public List<Schedule> findAllSchedule(LocalDate updateDate, String username) { // 일정 전체 조회

        String sql = "select * from calendar ";
        //데이터베이스 컬럼에 들어가 있는 데이터 값 : 2025-02-03 09:07:00 = 2025-02-03
        // 클라이언트에서 들어온값 : 2025-02-03
        //
        if (updateDate != null && (username != null && !username.isBlank())){ // 둘다 있을때
            sql += "WHERE DATE(updateDate) =:updateDate AND user =:username";
        }else if(updateDate != null && (username == null || username.isBlank())){ // updateDate만 있을때
            sql += "WHERE DATE(updateDate) =:updateDate";
        } else if(updateDate == null && (username != null && username.isBlank())){ // username만 있을때
            sql += "WHERE user =:username ";
        }
        sql += "ORDER BY updateDate DESC";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("updateDate", updateDate);

        return namedParameterJdbcTemplate.query(
                sql, param,
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
                "update calendar set todo = ?, user = ?, updateDate = ? where id = ?",
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
                "delete from calendar where id = ?",
                id
                //requestdto와 레포지토리에 있는게 같아야함.
        );

    }


}
