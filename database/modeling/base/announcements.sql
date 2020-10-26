drop table if exists announcements;

create table announcements
(
    ann_id       serial primary key,
    proj_id       int not null
        constraint announcements_crs_id_fkey references projects (proj_id),
    creator_id int not null
        constraint announcements_creator_id_fkey references teachers (tch_id),
    title varchar not null,
    message text,
    modified_date time default now()
);