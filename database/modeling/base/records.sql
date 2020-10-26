drop table if exists records cascade;

create table records
(
    rcd_id   serial primary key,
    proj_id       int  not null
        constraint records_proj_id_fkey references projects (proj_id),
    creator_id int not null
        constraint announcements_creator_id_fkey references teachers (tch_id),
    rcd_name varchar not null,
    type         char not null, -- N: none, C: comment, G: grade, S: score
    base_content varchar        -- base referencer content field, like ?/100.0
);