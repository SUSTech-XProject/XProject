select std_id,
       r.role_id,
       username,
       std_name,
       std_no,
       std_class,
       std.flags,
       sch_id,
       email
from project_insts as pi
         join proj_inst_student_rt pisr on pi.proj_inst_id = pisr.proj_inst_id
         join roles r on pisr.std_role_id = r.role_id
         join students as std on pisr.std_role_id = std.role_id
where pi.proj_inst_id = 1;


-- check student

select pi.*
from project_insts as pi
         join proj_inst_student_rt pisr on pi.proj_inst_id = pisr.proj_inst_id
where proj_id = 1
  and std_role_id = 1;

select p.proj_name, pi.status, pi.topic_str topic, pi.team_name, s.std_no, s.std_name
from project_insts pi
         join projects p on pi.proj_id = p.proj_id
         left join proj_inst_student_rt pisr on pi.proj_inst_id = pisr.proj_inst_id
         left join students s on pisr.std_role_id = s.role_id
where p.proj_id = 1
order by (pi.status, topic_str, team_name);