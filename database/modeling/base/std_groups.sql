drop table if exists std_groups;

create table std_groups
(
    sgp_id serial primary key,
    sch_id int  not null
        constraint std_group_sch_id_fkey references schools(sch_id),
    group_name varchar not null
);