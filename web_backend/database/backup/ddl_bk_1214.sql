create table roles
(
	role_id serial not null
		constraint roles_pkey
			primary key,
	role_type varchar not null,
	username varchar not null
		constraint roles_username_ukey
			unique,
	password varchar not null,
	status varchar default 'Enabled'::character varying not null,
	salt varchar not null,
	register_time timestamp with time zone default now(),
	icon_url varchar default 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'::character varying
);

comment on column roles.role_type is 'Teacher
Student
Admin';

alter table roles owner to xproj_super0;

create table admins
(
	admin_id serial not null
		constraint admins_pkey
			primary key,
	role_id integer not null
		constraint admins_role_id_fkey
			references roles
);

alter table admins owner to xproj_super0;

create table schools
(
	sch_id serial not null
		constraint schools_pkey
			primary key,
	sch_name varchar not null,
	location varchar
);

alter table schools owner to xproj_super0;

create table teachers
(
	tch_id serial not null
		constraint teachers_pkey
			primary key,
	role_id integer not null
		constraint teachers__role_id__fkey
			references roles,
	tch_name varchar,
	tch_no char(8),
	sch_id integer
		constraint teachers__sch_id__fkey
			references schools,
	email varchar
);

alter table teachers owner to xproj_super0;

create table logging
(
	log_time time default now() not null,
	role integer,
	opt varchar,
	message varchar
);

alter table logging owner to xproj_super0;

create table students
(
	std_id serial not null
		constraint students_pkey
			primary key,
	role_id integer not null
		constraint students_role_id_fkey
			references roles,
	std_name varchar(20),
	std_no char(8),
	std_class char(6),
	flags varchar default '[]'::character varying,
	sch_id integer
		constraint students_sch_id_fkey
			references schools,
	email varchar,
	skills varchar default '[]'::character varying,
	bio varchar default 'I have a great plan, but there isn''t enough room here.'::character varying
);

alter table students owner to xproj_super0;

create table test_table
(
	id integer,
	str varchar,
	num integer
);

alter table test_table owner to xproj_super0;

create table pms_roles
(
	pms_role_id serial not null
		constraint pms_roles_pkey
			primary key,
	name varchar,
	pr_desc varchar
);

alter table pms_roles owner to xproj_super0;

create table permissions
(
	pms_id serial not null
		constraint permissions_pkey
			primary key,
	pms_name varchar,
	pms_desc varchar,
	url varchar
);

alter table permissions owner to xproj_super0;

create table role_pms_role_rt
(
	role_pms_role_rt_id serial not null
		constraint role_pms_role_rt_pkey
			primary key,
	role_id integer
		constraint role_pms_role_rt__role_id__fkey
			references roles,
	pms_role_id integer
		constraint role_pms_role_rt__pms_role_id__fkey
			references pms_roles
);

alter table role_pms_role_rt owner to xproj_super0;

create table pms_role_pms_rt
(
	pms_role_pms_rt_id serial not null
		constraint pms_role_pms_rt_pkey
			primary key,
	pms_role_id integer
		constraint pms_role_pms_rt__pms_role_id__fkey
			references pms_roles,
	pms_id integer
		constraint pms_role_pms_rt__role_id__fkey
			references permissions
);

alter table pms_role_pms_rt owner to xproj_super0;

create unique index pms_role_pms_rt_pms_id_pms_role_id_uindex
	on pms_role_pms_rt (pms_id, pms_role_id);

create table projects
(
	proj_id serial not null
		constraint projects_pkey
			primary key,
	sch_id integer
		constraint projects__sch_id__fkey
			references schools,
	creator_id integer
		constraint projects__creator_id__fkey
			references roles,
	create_time timestamp default now(),
	proj_name varchar,
	course_name varchar,
	term varchar,
	description text,
	topics varchar default '[{"topic_name":"Default","max_team_size":-1}]'::character varying,
	proj_settings varchar default '{}'::character varying
);

alter table projects owner to xproj_super0;

create table submissions
(
	sbm_id serial not null
		constraint submissions_pkey
			primary key,
	proj_id integer not null
		constraint submissions_projects_proj_id_fk
			references projects,
	creator_id integer not null
		constraint submissions_roles_role_id_fk
			references roles,
	title varchar not null,
	description text,
	modified_time timestamp with time zone default now(),
	created_time timestamp with time zone default now(),
	due_time timestamp with time zone,
	final_time timestamp with time zone,
	max_sbm integer,
	status char,
	resources varchar
);

comment on column submissions.max_sbm is 'Number of resubmissions allowed';

alter table submissions owner to xproj_super0;

create table team_form_tasks
(
	tf_task_id serial not null
		constraint team_form_tasks_pkey
			primary key,
	proj_id integer not null
		constraint team_form_tasks_projects_proj_id_fk
			references projects,
	creator_id integer not null
		constraint team_form_tasks_roles_role_id_fk
			references roles,
	title varchar not null,
	description text,
	mode char not null,
	created_time time default now() not null
);

alter table team_form_tasks owner to xproj_super0;

create table project_insts
(
	proj_inst_id serial not null
		constraint project_insts_pkey
			primary key,
	proj_id integer
		constraint project_insts_projects_proj_id_fk
			references projects,
	topic_str varchar,
	description varchar,
	status varchar default 'Raw'::character varying,
	flags varchar default '[]'::character varying,
	team_name varchar,
	target_mem_num integer,
	icon_url varchar default 'https://data.ac-illust.com/data/thumbnails/f6/f6e3fd6d7e60544500352e46ad300085_t.jpeg'::character varying
);

comment on column project_insts.status is 'Raw
Confirm
Dissolve';

comment on column project_insts.target_mem_num is 'Dreaming member number';

alter table project_insts owner to xproj_super0;

create table submission_insts
(
	sbm_inst_id serial not null
		constraint submission_insts_pkey
			primary key,
	sbm_id integer not null
		constraint submission_insts_sbm_id_fkey
			references submissions,
	proj_inst_id integer not null
		constraint submission_insts_project_insts_proj_inst_id_fk
			references project_insts
				on update cascade on delete cascade,
	submit_time time default now() not null,
	submitter_id integer not null
		constraint submission_insts_roles_role_id_fk
			references roles,
	sbm_left integer,
	attachments varchar
);

alter table submission_insts owner to xproj_super0;

create table records
(
	rcd_id serial not null
		constraint records_pkey
			primary key,
	proj_id integer
		constraint records_projects_proj_id_fk
			references projects,
	creator_id integer
		constraint records__creator_id__fkey
			references roles,
	rcd_name varchar,
	type varchar,
	base_content varchar,
	created_time timestamp with time zone default now(),
	derived varchar
);

comment on column records.type is 'None
Comment
Grade
Point';

comment on column records.base_content is 'base referencer content field, like ?/100.0';

alter table records owner to xproj_super0;

create table announcements
(
	ann_id serial not null
		constraint announcements_pkey
			primary key,
	proj_id integer
		constraint announcements_projects_proj_id_fk
			references projects,
	creator_id integer
		constraint announcements__creator_id__fkey
			references roles,
	title varchar,
	message text,
	modified_time timestamp with time zone default now(),
	created_time timestamp with time zone default now()
);

alter table announcements owner to xproj_super0;

create table event_arrange_tasks
(
	ea_task_id serial not null
		constraint event_arrange_tasks_pkey
			primary key,
	proj_id integer
		constraint event_arrange_tasks__proj_id__fkey
			references projects,
	creator_id integer
		constraint event_arrange_tasks__creator_id__fkey
			references roles,
	title varchar,
	description text,
	mode char,
	created_time timestamp with time zone default now(),
	modified_time timestamp with time zone
);

alter table event_arrange_tasks owner to xproj_super0;

create table project_role_rt
(
	proj_role_id serial not null
		constraint project_role_rt_pkey
			primary key,
	proj_id integer
		constraint project_role_rt_projects_proj_id_fk
			references projects,
	role_id integer
		constraint project_role_rt_roles_role_id_fk
			references roles,
	group_mark varchar
);

alter table project_role_rt owner to xproj_super0;

create table role_groups
(
	rgp_id serial not null
		constraint role_groups_pkey
			primary key,
	role_id integer,
	group_name varchar,
	description varchar
);

alter table role_groups owner to xproj_super0;

create table role_group_role_rt
(
	rgp_role_rt_id serial not null
		constraint role_group_role_rt_pkey
			primary key,
	rgp_id integer
		constraint role_group_role_rt_role_groups_rgp_id_fk
			references role_groups,
	role_id integer
		constraint role_group_role_rt_roles_role_id_fk
			references roles
);

alter table role_group_role_rt owner to xproj_super0;

create table record_insts
(
	rcd_inst_id serial not null
		constraint record_insts_pkey
			primary key,
	rcd_id integer
		constraint record_insts_records_rcd_id_fk
			references records,
	role_id integer
		constraint record_insts_roles_role_id_fk
			references roles,
	modified_time timestamp default now(),
	content varchar,
	comments varchar
);

alter table record_insts owner to xproj_super0;

create table proj_inst_student_rt
(
	proj_inst_rt_id serial not null
		constraint proj_inst_student_rt_pkey
			primary key,
	proj_inst_id integer
		constraint proj_inst_student_rt_project_insts_proj_inst_id_fk
			references project_insts
				on update cascade on delete cascade,
	std_role_id integer
		constraint proj_inst_student_rt_roles_role_id_fk
			references roles,
	opt char,
	opt_time timestamp with time zone default now()
);

comment on column proj_inst_student_rt.opt is 'I: in
O: out';

alter table proj_inst_student_rt owner to xproj_super0;

create table records_derived_rt
(
	rcd_rt_id serial not null
		constraint records_derived_rt_pkey
			primary key,
	rcd_id integer
		constraint records_derived_rt_records_rcd_id_fk
			references records,
	derived_id integer
		constraint records_derived_rt_records_rcd_id_fk_2
			references records,
	coefficient varchar
);

alter table records_derived_rt owner to xproj_super0;

create table messages
(
	msg_id serial not null
		constraint messages_pk
			primary key,
	proj_inst_id integer
		constraint messages_project_insts_proj_inst_id_fk
			references project_insts,
	type varchar,
	title varchar,
	content varchar,
	creator_role_id integer
		constraint messages_roles_role_id_fk
			references roles,
	created_time timestamp with time zone default now(),
	status varchar default '{}'::character varying
);

comment on column messages.type is 'Apply
Quit
Operation';

comment on column messages.status is 'json';

alter table messages owner to xproj_super0;

create table event_insts
(
	event_id serial not null
		constraint event_insts_pk
			primary key,
	date date,
	start_time time,
	end_time time,
	ea_task_id integer
		constraint event_insts_event_arrange_tasks_ea_task_id_fk
			references event_arrange_tasks
				on update cascade on delete cascade,
	week varchar,
	status varchar default 'Valid'::character varying,
	proj_inst_id integer
		constraint event_insts_project_insts_proj_inst_id_fk
			references project_insts
				on update cascade
);

comment on column event_insts.status is 'Valid
Selected
Disable';

alter table event_insts owner to xproj_super0;

