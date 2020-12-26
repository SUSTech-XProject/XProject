drop table if exists records_derived_rt;

create table records_derived_rt
(
    rcd_rt_id serial primary key,
    rcd_id int,
    derived_id int,
    coefficient double precision
)