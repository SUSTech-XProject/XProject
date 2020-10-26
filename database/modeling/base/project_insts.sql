drop table if exists project_insts;

create table project_insts
(
    proj_inst_id serial primary key,
    proj_id int  not null
        constraint project_insts_proj_id_fkey references projects(proj_id),
    team_id int not null
        constraint project_insts_team_id_fkey references teams (team_id),
    topic_str varchar,
    description varchar not null
);