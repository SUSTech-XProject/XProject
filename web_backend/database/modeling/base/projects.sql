drop table if exists projects cascade;

create table projects
(
    proj_id     serial primary key,
    sch_id      int,
    creator_id  int,
    create_time timestamp default now(),
    proj_name   varchar,
    course_name varchar,
    term        varchar,
    description text
);

-- alter table projects
--     drop
--         constraint projects__sch_id__fkey;
alter table projects
    add
        constraint projects__sch_id__fkey
            foreign key (proj_id) references schools (sch_id);

-- alter table projects
--     drop
--         constraint projects__creator_id__fkey;
alter table projects
    add
        constraint projects__creator_id__fkey
            foreign key (creator_id) references roles (role_id);