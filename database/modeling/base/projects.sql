drop table if exists projects cascade;

create table projects
(
    proj_id   serial primary key,
    sch_id        int     not null
        constraint projects_sch_id_fkey references schools (sch_id),
    creator_id    int     not null
        constraint projects_creator_id_fkey references teachers (tch_id),
    proj_name varchar not null,
    description   text
);