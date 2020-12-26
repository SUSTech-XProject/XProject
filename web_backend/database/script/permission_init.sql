select *
from roles;

select *
from pms_roles;

select *
from permissions;

insert into pms_roles (name, pr_desc)
values ('default_admin', 'Default permission set of admins');
insert into pms_roles (name, pr_desc)
values ('default_teacher', 'Default permission set of teachers');
insert into pms_roles (name, pr_desc)
values ('default_student', 'Default permission set of students');

insert into permissions (pms_name, pms_desc, url)
values ('getAllPms', 'Get all permissions (of current user) by username', '\api\permissions');
insert into pms_role_pms_rt (pms_role_id, pms_id)
values (1, 1);
insert into pms_role_pms_rt (pms_role_id, pms_id)
values (2, 1);
insert into pms_role_pms_rt (pms_role_id, pms_id)
values (3, 1);

insert into role_pms_role_rt (role_id, pms_role_id)
values (1, 3);
insert into role_pms_role_rt (role_id, pms_role_id)
values (2, 2);