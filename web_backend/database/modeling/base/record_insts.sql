drop table if exists record_insts;

create table record_insts
(
    rcd_inst_id serial primary key,
    rcd_id      int,
    role_id     int,
    modified_time timestamp default now(),
    content     varchar,
    comments    varchar
);

-- alter table record_insts
--     drop
--         constraint record_insts__rcd_id__fkey;
alter table record_insts
    add
        constraint record_insts__rcd_id__fkey
            foreign key (rcd_id) references records (rcd_id);

-- alter table record_insts
--     drop
--         constraint record_insts__role_id__fkey;
alter table record_insts
    add
        constraint record_insts__role_id__fkey
            foreign key (role_id) references roles (role_id);