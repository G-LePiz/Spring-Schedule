use calendar;

-- 테이블 생성
create table calendar (
                          id int auto_increment primary key,
                          todo varchar(255) not null,
                          user varchar(100) not null,
                          password varchar(100) not null,
                          createDate timestamp default current_timestamp,
                          updateDate timestamp default current_timestamp on update current_timestamp
);