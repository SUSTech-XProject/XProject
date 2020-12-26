select * from announcements;

-- std_id, r.role_id, username, std_name, std_no, std_class, std.flags, sch_id, email, group_mark, pi.proj_inst_id, topic_str, pi.status

select ann.*, tch_name, email
from announcements as ann
    join teachers as tch on tch.role_id = ann.creator_id
where proj_id = 1;
