# 일정 관리 앱

## API 목록

| Method | URL             | RequestParam         | Response                                                                                            | Request                                                                                                          | Description | StatusCode | 
|--------|-----------------|----------------------|-----------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|------------|------------|
| POST   | /schedules      | -                    | {<br/>"todo":"놀기"<br/>"user":"최다원"<br/>"createDate: YYYY-MM-DD<br/>"updateDate: YYYY-MM-DD<br/>}    | {<br/>"todo":"놀기"<br/>"user":"최다원"<br/>"password":"1234"<br/>}                                                   | 일정 생성      | 일정 등록: 200 | 
| GET    | /schedules      | -                    | {<br/>"todo":"놀기"<br/>"user":"최다원"<br/>"createDate: YYYY-MM-DD<br/>"updateDate: YYYY-MM-DD<br/>}    | http://localhost:8080/api/schedules                                                                              |         | 일정 조회      | 일정 조회: 200 |
| GET    | /schedules/{id} | updateDate, username | {<br/>"todo":"놀기"<br/>"user":"최다원"<br/>"createDate: YYYY-MM-DD<br/>"updateDate: YYYY-MM-DD<br/>}... | http://localhost:8080/api/schedules/2                                                                            |                                                                | 단건 일정 조회   |일정 조회: 200|
| PUT    | /schedules/{id} | -                    |     {<br/>"todo":"놀기2"<br/>"user":"최다원2"<br/>"createDate: YYYY-MM-DD<br/>"updateDate: YYYY-MM-DD<br/>}                                                                                                | http://localhost:8080/api/schedules/2      <br/>{<br/>"todo":"놀기2"<br/>"user":"최다원2"<br/>"password":"1234"<br/>} | 일정 수정      |   일정 수정: 200         |
| DELETE | /schedules/{id} | -                    |                                                                                                     | http://localhost:8080/api/schedules/2<br/>{<br/>"password":"1234"<br/>}                                          | 일정 삭제      |     일정 삭제: 200       |


## ERD
![ERD](images/ERD.png)