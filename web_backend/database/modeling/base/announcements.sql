drop table if exists announcements;

create table announcements
(
    ann_id        serial primary key,
    proj_id       int,
    creator_id    int,
    title         varchar,
    message       text,
    modified_date timestamptz default now()
);

-- alter table announcements
--     drop
--         constraint announcements__crs_id__fkey;
alter table announcements
    add
        constraint announcements__crs_id__fkey
            foreign key (proj_id) references projects (proj_id);

-- alter table announcements
--     drop
--         constraint announcements__creator_id__fkey;
alter table announcements
    add
        constraint announcements__creator_id__fkey
            foreign key (creator_id) references roles (role_id);