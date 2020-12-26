drop table if exists project_insts;

create table project_insts
(
    proj_inst_id serial primary key,
    proj_id int,
    team_id int,
    topic_str varchar,
    description varchar
);

-- alter table project_insts
--     drop
--         constraint project_insts__proj__id_fkey;
alter table project_insts
    add
        constraint project_insts__proj__id_fkey
            foreign key (proj_id) references projects (proj_id);

-- alter table project_insts
--     drop
--         constraint project_insts__team_id__fkey;
alter table project_insts
    add
        constraint project_insts__team_id__fkey
            foreign key (team_code) references teams (team_id);