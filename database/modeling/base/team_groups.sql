drop table if exists team_groups;

create table team_groups
(
    tgp_id     serial primary key,
    proj_id    int     not null
        constraint team_groups_proj_id_fkey references projects (proj_id),
    group_name varchar not null
);