drop table if exists admins;

create table admins
(
    admin_id serial primary key,
    role_id  int not null
        constraint admins_role_id_fkey references roles (role_id)
);


