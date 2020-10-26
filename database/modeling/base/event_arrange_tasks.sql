drop table if exists event_arrange_tasks;

create table event_arrange_tasks
(
    ea_task_id       serial primary key,
    proj_id       int not null
        constraint event_arrage_tasks_proj_id_fkey references projects (proj_id),
    creator_id int not null
        constraint event_arrage_tasks_creator_id_fkey references teachers (tch_id),
    title varchar not null,
    description text,
    mode char not null,         -- A: automatic, R: race, M: manual
    modified_date time not null default now()
);