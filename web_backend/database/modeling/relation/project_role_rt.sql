-- change from project_student_rt

drop table if exists project_role_rt;

create table project_role_rt
(
    proj_role_id serial primary key,
    proj_id int,
    role_id int
);