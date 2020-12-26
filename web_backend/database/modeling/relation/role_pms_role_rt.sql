drop table if exists role_pms_role_rt;

create table role_pms_role_rt
(
    role_pms_role_rt_id serial primary key,
    role_id             int,
    pms_role_id         int
);

alter table role_pms_role_rt
    drop
        constraint role_pms_role_rt__role_id__fkey;
alter table role_pms_role_rt
    add
        constraint role_pms_role_rt__role_id__fkey
            foreign key (role_id) references roles (role_id);

alter table role_pms_role_rt
    drop
        constraint role_pms_role_rt__pms_role_id__fkey;
alter table role_pms_role_rt
    add
        constraint role_pms_role_rt__pms_role_id__fkey
            foreign key (pms_role_id) references pms_roles (pms_role_id);