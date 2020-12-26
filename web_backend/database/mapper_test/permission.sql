with q as (select role_id from roles where username = 'testteacher0')
select p.*
from q
         join role_pms_role_rt r_pr on q.role_id = r_pr.role_id
         join pms_roles pr on r_pr.pms_role_id = pr.pms_role_id
         join pms_role_pms_rt pr_p on pr.pms_role_id = pr_p.pms_role_id
         join permissions p on pr_p.pms_id = p.pms_id;

select count(*) != 0 as exist
from (select url
      from permissions
      where url = '\api\permissions') as q