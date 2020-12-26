drop table if exists pms_role_pms_rt;

create table pms_role_pms_rt
(
    pms_role_pms_rt_id serial primary key,
    pms_role_id        int,
    pms_id             int
);


alter table pms_role_pms_rt
    drop
        constraint pms_role_pms_rt__pms_role_id__fkey;
alter table pms_role_pms_rt
    add
        constraint pms_role_pms_rt__pms_role_id__fkey
            foreign key (pms_role_id) references pms_roles (pms_role_id);


alter table pms_role_pms_rt
    drop
        constraint pms_role_pms_rt__role_id__fkey;
alter table pms_role_pms_rt
    add
        constraint pms_role_pms_rt__role_id__fkey
            foreign key (pms_id) references permissions (pms_id);
