drop table if exists event_arrange_tasks;

create table event_arrange_tasks
(
    ea_task_id    serial primary key,
    proj_id       int,
    creator_id int,
    title varchar,
    description text,
    mode char,         -- A: automatic, R: race, M: manual
    create_time timestamptz default now(),
    modified_date time not null default now()
);

-- alter table event_arrange_tasks
--     drop
--         constraint event_arrange_tasks__proj_id__fkey;
alter table event_arrange_tasks
    add
        constraint event_arrange_tasks__proj_id__fkey
            foreign key (proj_id) references projects (proj_id);

-- alter table event_arrange_tasks
--     drop
--         constraint event_arrange_tasks__creator_id__fkey;
alter table event_arrange_tasks
    add
        constraint event_arrange_tasks__creator_id__fkey
            foreign key (creator_id) references roles (role_id);