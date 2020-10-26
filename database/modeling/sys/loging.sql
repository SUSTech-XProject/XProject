drop table if exists logging;

create table logging(
    log_time time not null default now(),
    role int,
    opt varchar,
    message varchar
);