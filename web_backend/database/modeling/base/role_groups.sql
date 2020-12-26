-- change from std_groups

drop table if exists role_groups;

create table role_groups
(
    rgp_id serial primary key,
    role_id int,
    group_name varchar,
    description varchar
);