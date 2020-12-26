-- change from std_group_student_rt

drop table if exists role_group_role_rt;

create table role_group_role_rt
(
    rgp_role_rt_id serial primary key,
    rgp_id int,
    role_id int
)