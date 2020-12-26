create or replace function generate_score(proj_id_p int,
                               rcd_id_p int,
                               modified_role_id_p int,
                               rcd_id_list int array,
                               coe_list double precision array) returns int
as
$$
declare
    new_base_content int;
begin
    create local temp table if not exists new_rcd
    (
        role_id       int,
        content      double precision
    );
    truncate new_rcd;

    insert into new_rcd
    select role_id, round(cast(sum(new_score) as numeric), 2)
    from (with scores as ( -- get the student with coe info
        with rcd_coe as ( -- get the coe param table
            select rcd_id as rcd_id, coe / 100.0 as coe
            from (select ROW_NUMBER() OVER (ORDER BY rcd_id) AS idx, rcd_id
                  from unnest(rcd_id_list) as rcd_id) as t1
                     join (select ROW_NUMBER() OVER (ORDER BY coe) AS idx, coe
                           from unnest(coe_list) as coe) as t2
                          on t1.idx = t2.idx)
        select std.role_id,
               coalesce(cast(ri.content as double precision), 0.0) as content,
               rc.coe
        from rcd_coe as rc
                 join records as rcd on rcd.rcd_id = rc.rcd_id
            and rcd.type = 'Point'
            and rcd.proj_id = proj_id_p
                 join project_role_rt prr on prr.proj_id = proj_id_p
                 join students as std on prr.role_id = std.role_id
                 left join record_insts ri on rcd.rcd_id = ri.rcd_id
            and std.role_id = ri.role_id
    )
          select role_id, content * coe as new_score
          from scores) as t
    group by role_id;

    select round(cast(sum(new_content) as numeric), 2) into new_base_content
    from (with rcd_temp as (
            with rcd_coe as ( -- get the coe param table
                select rcd_id as rcd_id, coe / 100.0 as coe
                from (select ROW_NUMBER() OVER (ORDER BY rcd_id) AS idx, rcd_id
                      from unnest(rcd_id_list) as rcd_id) as t1
                         join (select ROW_NUMBER() OVER (ORDER BY coe) AS idx, coe
                               from unnest(coe_list) as coe) as t2
                              on t1.idx = t2.idx)
            select cast(base_content as double precision) as base_content,
                   rc.coe
            from records as rcd
                     join rcd_coe as rc on rcd.rcd_id = rc.rcd_id
            where proj_id = proj_id_p
              and rcd.type = 'Point')
        select base_content * coe as new_content
        from rcd_temp) temp;

    update records set base_content = new_base_content
    where rcd_id = rcd_id_p;

    insert into record_insts (rcd_id, role_id, modified_time, content, comments, modified_role_id)
    select rcd_id_p, role_id, now(), content, 'Generated record', modified_role_id_p
    from new_rcd;

    return (select count(*) from new_rcd);
end;
$$
language plpgsql;

select success_cnt
from generate_score(1, 13, 2, array[1, 2, 3], array[15.0, 16.0, 17.0]) as success_cnt;


select a1, a2
from (select ROW_NUMBER() OVER (ORDER BY a1) AS idx, a1
      from unnest(array [1, 2]) as a1) as t1
         join (select ROW_NUMBER() OVER (ORDER BY a2) AS idx, a2
               from unnest(array [4, 5]) as a2) as t2
              on t1.idx = t2.idx;

select role_id, sum(new_score)
from (with scores as ( -- get the student with coe info
    with rcd_coe as ( -- get the coe param table
        select rcd_id as rcd_id, coe / 100.0 as coe
        from (select ROW_NUMBER() OVER (ORDER BY rcd_id) AS idx, rcd_id
              from unnest(array [1, 2, 3, 4]) as rcd_id) as t1
                 join (select ROW_NUMBER() OVER (ORDER BY coe) AS idx, coe
                       from unnest(array [15.0, 16.0, 17.0, 18.0]) as coe) as t2
                      on t1.idx = t2.idx)
    select std.role_id,
           coalesce(cast(ri.content as double precision), 0.0) as content,
           rc.coe
    from rcd_coe as rc
             join records as rcd on rcd.rcd_id = rc.rcd_id
        and rcd.type = 'Point'
        and rcd.proj_id = 1
             join project_role_rt prr on prr.proj_id = 1
             join students as std on prr.role_id = std.role_id
             left join record_insts ri on rcd.rcd_id = ri.rcd_id
        and std.role_id = ri.role_id
)
      select role_id, content * coe as new_score
      from scores) as t
group by role_id;

select round(cast(sum(new_content) as numeric), 2)
from (with rcd_temp as (
        with rcd_coe as ( -- get the coe param table
            select rcd_id as rcd_id, coe / 100.0 as coe
            from (select ROW_NUMBER() OVER (ORDER BY rcd_id) AS idx, rcd_id
                  from unnest(array [1, 2, 3, 4]) as rcd_id) as t1
                     join (select ROW_NUMBER() OVER (ORDER BY coe) AS idx, coe
                           from unnest(array [15.0, 16.0, 17.0, 18.0]) as coe) as t2
                          on t1.idx = t2.idx)
        select cast(base_content as double precision) as base_content,
               rc.coe
        from records as rcd
                 join rcd_coe as rc on rcd.rcd_id = rc.rcd_id
        where proj_id = 1
          and rcd.type = 'Point')
    select base_content * coe as new_content
    from rcd_temp) temp



    select std_name, pi.proj_id, group_mark
    from students as std
      join roles r on std.role_id = r.role_id
      join project_role_rt as prr on std.role_id = prr.role_id
      left join proj_inst_student_rt as pisr on pisr.std_role_id = r.role_id and pisr.proj_inst_id = 9
      left join project_insts pi on pisr.proj_inst_id = pi.proj_inst_id
--     where pi.proj_inst_id = 9;


select
    std_id, r.role_id, username, std_name, std_no, std_class, std.flags, sch_id, email, r.icon_url, pi.proj_inst_id, topic_str, pi.status

from project_insts as pi
    join proj_inst_student_rt pisr on pi.proj_inst_id = pisr.proj_inst_id
    join roles r on pisr.std_role_id = r.role_id
    join students as std on r.role_id = std.role_id
    join project_role_rt prr on r.role_id = prr.role_id and prr.proj_id = pi.proj_id
where pi.proj_inst_id = 9;


select
messages.*,
    c.role_type as crt_role_type,
    c.username as crt_username,
    s1.std_name as crt_std_name,
    c.icon_url as crt_icon_url,
    h.role_type as hdl_role_type,
    h.username as hdl_username,
    s2.std_name as hdl_std_name,
    h.icon_url as hdl_icon_url
from messages
      left join roles as c on messages.creator_role_id = c.role_id
        join students as s1 on c.role_id = s1.role_id
      left join roles as h on messages.handler_role_id = h.role_id
        left join students as s2 on h.role_id = s2.role_id
    where proj_inst_id = 9
