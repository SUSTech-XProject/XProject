drop table if exists teachers;

create table teachers
(
    tch_id serial primary key,
    role_id  int not null
        constraint teachers_role_id_fkey references roles (role_id),

    tch_name varchar not null,
    tch_no char(8) not null
);
