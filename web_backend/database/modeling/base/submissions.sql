drop table if exists submissions cascade;

create table submissions
(
    sbm_id   serial primary key,
    proj_id        int     not null
        constraint submissions_proj_id_fkey references projects (proj_id),
    creator_id    int     not null
        constraint submissions_creator_id_fkey references teachers (tch_id),
    title varchar not null,
    description   text,
    due time,
    modified_time time
);