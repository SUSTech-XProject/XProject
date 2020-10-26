drop table if exists teams;

create table teams
(
    team_id   serial primary key,
    tgp_id    int     not null
        constraint teams_tgp_id_fkey references team_groups (tgp_id),
    team_code varchar not null,
    status    char    not null, -- R: raw, G: recognized, C: cancel
    flags     varchar           -- flag label string for this team
);