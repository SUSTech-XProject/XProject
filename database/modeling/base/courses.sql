drop table if exists courses cascade;

create table courses
(
    crs_id serial primary key,
    sch_id int not null
        constraint courses_sch_id_fkey references schools(sch_id),
    crs_code varchar not null,
    crs_name varchar not null,
    description varchar,
    term char(6) not null        -- like 2020-f
);