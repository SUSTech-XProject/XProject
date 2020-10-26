drop table if exists team_form_tasks;

create table team_form_tasks
(
    tf_task_id       serial primary key,
    proj_id       int not null
        constraint team_form_tasks_proj_id_fkey references projects (proj_id),
    tgp_id  int not null
        constraint team_form_tasks_tgp_id_fkey references team_groups (tgp_id),
    creator_id int not null
        constraint team_form_tasks_creator_id_fkey references teachers (tch_id),
    title varchar not null,
    description text,
    mode char not null,         -- A: automatic, R: recruit, M: manual
    modified_date time not null default now()
);