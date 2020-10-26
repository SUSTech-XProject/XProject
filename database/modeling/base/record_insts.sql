drop table if exists record_insts;

create table record_insts
(
    rcd_inst_id serial primary key,
    rcd_id      int     not null
        constraint record_insts_rcd_id_fkey references records (rcd_id),
    std_id      int     not null
        constraint record_insts_std_id_fkey references students (std_id),
    content     varchar not null
);