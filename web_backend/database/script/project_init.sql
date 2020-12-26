select * from schools;

select * from teachers;

select * from projects;

insert into schools (sch_name) values ('SUSTech');

insert into projects (sch_id, creator_id, proj_name, description)
values (1, 1, 'OOAD Course Project', 'CS309 OOAD fall2020 course project');

insert into projects (sch_id, creator_id, proj_name, description)
values (1, 1, 'Database Principle A Course Project', 'Database for 12306. CS307A DBP spring2019');

insert into projects (sch_id, creator_id, proj_name, description)
values (1, 1, 'Digital logic A Course Project', 'CS207A DD fall2019');

select * from students;

select * from project_student_rt;
insert into project_student_rt (proj_id, std_id) VALUES (1, 5);
insert into project_student_rt (proj_id, std_id) VALUES (2, 5);
insert into project_student_rt (proj_id, std_id) VALUES (3, 5);

select *
from projects as p
    join project_student_rt psr on p.proj_id = psr.proj_id
where std_id = 5