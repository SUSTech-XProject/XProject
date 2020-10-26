drop table if exists roles;

create table roles
(
    role_id   serial primary key,
    role_type char    not null, -- A: admin, T: teacher / SA, S: student
    username  varchar not null
        constraint roles_username_1ukey unique,
    password  varchar not null
);