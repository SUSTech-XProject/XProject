drop table if exists permissions;

create table permissions
(
    pms_id serial primary key,
    pms_name varchar,
    pms_desc varchar,
    url varchar
);



