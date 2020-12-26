select *
from records;

select *
from record_insts;

select r.rcd_id,
       ri.rcd_inst_id,
       rcd_name,
       r.type,
       created_time,
       modified_time,
       r.derived,
       t.tch_name,
       t.email,
       ri.content,
       r.base_content,
       comments
from records as r
         join record_insts ri on r.rcd_id = ri.rcd_id
         join teachers t on r.creator_id = t.role_id
where proj_id = 1
  and ri.role_id = 1
order by rcd_id;

select r.rcd_id,
       ri.rcd_inst_id,
       rcd_name,
       r.type,
       created_time,
       modified_time,
       r.derived,
       ri.content,
       r.base_content,
       comments,
       t1.tch_name,
       t1.email,
       t2.tch_name,
       t2.email
from records as r
         join teachers t1 on r.creator_id = t1.role_id
         left join record_insts ri on r.rcd_id = ri.rcd_id and ri.role_id = 1
         left join teachers t2 on ri.modified_role_id = t2.role_id
where proj_id = 1
order by rcd_id;


select string_agg(coe || '#' || name, ',' order by derived_id) as derived
from (select r.rcd_id as result_id, r2.rcd_id as derived_id, r2.rcd_name as name, coefficient as coe
      from records as r
               join records_derived_rt rdr on r.rcd_id = rdr.rcd_id
               join records as r2 on rdr.derived_id = r2.rcd_id) t;


select rcd.rcd_name, s.std_no, s.std_name, content, rcd.type, comments
from records rcd
         join record_insts ri on rcd.rcd_id = ri.rcd_id
         join students s on ri.role_id = s.role_id
where rcd.proj_id = 1;


select ri.*
from records rcd
         join record_insts ri on rcd.rcd_id = ri.rcd_id
         join students s on ri.role_id = s.role_id
where rcd.proj_id = 1
  and rcd_name = 'assignment1'
  and std_no = '11816401';

select role_id
from students s
where std_no = '11816401';

select rcd_id
from records
where proj_id = 1
  and rcd_name = 'assignment1';

-- GradeDTO

select *
from records as rcd
         join project_insts pi on rcd.proj_id = pi.proj_id;

select std_id,
       std.role_id,
       r.icon_url,
       std_no,
       std_name,
       rcd_inst_id,
       rcd.type,
       ri.modified_time,
       rcd.derived,
       ri.content,
       rcd.base_content,
       ri.comments,
       tch_name,
       tch.email
from project_insts as pi
         join records as rcd on pi.proj_id = rcd.proj_id
         join proj_inst_student_rt pisr on pi.proj_inst_id = pisr.proj_inst_id
         join students as std on std.role_id = pisr.std_role_id
         join roles as r on std.role_id = r.role_id
         left join record_insts ri on std.role_id = ri.role_id and rcd.rcd_id = ri.rcd_id
         left join teachers as tch on tch.role_id = ri.modified_role_id
where pi.proj_inst_id = 9
  and rcd.rcd_id = 8;


select std_id,
       std.role_id,
       r.icon_url,
       std_no,
       std_name,
       rcd_inst_id,
       rcd.type,
       ri.modified_time,
       rcd.derived,
       ri.content,
       rcd.base_content,
       ri.comments,
       tch_name,
       tch.email
from students as std
         left join record_insts as ri on std.role_id = ri.role_id and ri.rcd_id = 1
         cross join records as rcd
         join roles as r on std.role_id = r.role_id
         join teachers as tch on tch.role_id = rcd.creator_id
where std.role_id = 1
  and rcd.rcd_id = 1;


-- combine

select ri.*
from record_insts as ri
where ri.rcd_id in (1, 2);

select count(*) = 1 as exist
from project_role_rt
where role_id = 5
  and proj_id = 1

select pi.*
from project_insts as pi
         join proj_inst_student_rt pisr on pi.proj_inst_id = pisr.proj_inst_id
where proj_id = 7
  and std_role_id = 33;

select *
from proj_inst_student_rt
where std_role_id = 33;

select *
from students
where std_no = '11818010' where role_id = 5 and proj_id = 1;


select rcd.rcd_name, s.std_no, s.std_name, content, rcd.type, comments
from records rcd
         join project_role_rt prr on rcd.proj_id = prr.proj_id
         join students s on prr.role_id = s.role_id
         left join record_insts ri on rcd.rcd_id = ri.rcd_id and ri.role_id = s.role_id
where rcd.proj_id = 7;

update students
set payload = '{}'
where true

with teams as (
    select std_role_id, pi.*
    from proj_inst_student_rt as pisr2
             join project_insts as pi on pisr2.proj_inst_id = pi.proj_inst_id
    where pi.proj_id = 8
)
select
    std_id, r.role_id, username, std_name, std_no, std_class, std.flags, sch_id, email, r.icon_url, group_mark, teams.proj_inst_id, team_name, topic_str, teams.status
from students as std
         join roles r on std.role_id = r.role_id
         join project_role_rt as prr on std.role_id = prr.role_id and prr.proj_id = 8
         left join teams on teams.std_role_id = r.role_id;


select r.rcd_id,
       ri.rcd_inst_id,
       rcd_name,
       r.type,
       created_time,
       modified_time,
       r.derived,
       ri.content,
       r.base_content,
       comments,
       t1.tch_name,
       t1.email,
       t2.tch_name,
       t2.email
from records as r
         join teachers t1 on r.creator_id = t1.role_id
         left join record_insts ri on r.rcd_id = ri.rcd_id and ri.role_id = 29
         left join teachers t2 on ri.modified_role_id = t2.role_id
where proj_id = 7
order by rcd_id;