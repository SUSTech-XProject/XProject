-- change from team_student_rt

drop table if exists proj_inst_student_rt;

create table proj_inst_student_rt
(
    proj_inst_rt_id serial primary key not null,
    proj_inst_id int,
    std_role_id int
)