select *
from students;

-- std_id, r.role_id, username, std_name, std_no, std_class, std.flags, sch_id, email, group_mark, pi.proj_inst_id, topic_str, pi.status

select std_id,
       r.role_id,
       username,
       std_name,
       std_no,
       std_class,
       std.flags,
       sch_id,
       email
       group_mark,
       pi.proj_inst_id,
       topic_str,
       pi.status
from students as std
         join roles r on std.role_id = r.role_id
         join project_role_rt as prr on std.role_id = prr.role_id
         left join proj_inst_student_rt as pisr on pisr.std_role_id = r.role_id
         left join project_insts pi on pisr.proj_inst_id = pi.proj_inst_id
where prr.proj_id = 1;

