drop table if exists students cascade;      -- + records

create table students
(
    std_id  serial primary key,
    role_id int not null
        constraint students_role_id_fkey references roles (role_id),
    sch_id        int     not null
        constraint projects_sch_id_fkey references schools (sch_id),
    std_name varchar(20) not null,
    std_no char(8) not null,
    std_class char(6) not null,     -- like 202219
    flags   varchar -- flag label string for this team
)