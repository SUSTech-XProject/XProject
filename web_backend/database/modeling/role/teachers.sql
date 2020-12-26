drop table if exists teachers;

create table teachers
(
    tch_id serial primary key,
    role_id  int
        constraint teachers_role_id_fkey references roles (role_id),
    tch_name varchar,
    tch_no char(8)
);

alter sequence teachers_tch_id_seq restart with 2;