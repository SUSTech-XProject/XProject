drop table if exists roles;

create table roles
(
    role_id   serial primary key,
    role_type char    not null, -- A: admin, T: teacher / SA, S: student
    username  varchar not null
        constraint roles_username_1ukey unique,
    password  varchar not null
);

alter table roles add column
    status char not null default 'V';       -- V: valid, D: deprecated

alter table roles add constraint
    roles_username_ukey unique (username);

alter sequence roles_role_id_seq restart;

truncate roles cascade;

delete from roles where username = 'admin1';
insert into roles (role_type, username, password)
values ('A', 'admin1', 'admin1');

delete from roles where username = 'testchertest1';
insert into roles (role_type, username, password)
values ('T', 'teachertest1', 'teachertest1');

delete from roles where username = 'studenttest1';
insert into roles (role_type, username, password)
values ('S', 'studenttest1', 'studenttest1');