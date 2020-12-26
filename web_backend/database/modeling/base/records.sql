drop table if exists records cascade;

create table records
(
    rcd_id       serial primary key,
    proj_id      int,
    creator_id   int,
    rcd_name     varchar,
    type         char,   -- N: none, C: comment, G: grade, S: score
    base_content varchar -- base referencer content field, like ?/100.0
);

-- alter table records
--     drop
--         constraint records__proj_id__fkey;
alter table records
    add
        constraint records__proj_id__fkey
            foreign key (proj_id) references projects (proj_id);

-- alter table records
--     drop
--         constraint records__creator_id__fkey;
alter table records
    add
        constraint records__creator_id__fkey
            foreign key (creator_id) references roles (role_id);