drop table if exists teams;

create table teams
(
    team_id   serial primary key,
    proj_id       int not null
        constraint team_form_tasks_proj_id_fkey references projects (proj_id),
    team_code varchar not null,
    status    char    not null, -- R: raw, G: recognized, C: cancel
    flags     varchar           -- flag label string for this team
);