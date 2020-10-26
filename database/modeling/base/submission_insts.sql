drop table if exists submission_insts;

create table submission_insts
(
    sbm_inst_id       serial primary key,
    sbm_id   int  not null
        constraint submission_insts_sbm_id_fkey references submissions (sbm_id),
    team_id      int  not null
        constraint submission_insts_team_id_fkey references teams (team_id),
    submit_time  time not null default now(),
    submitter_id int  not null
        constraint record_insts_submitter_id_fkey references students (std_id)
);