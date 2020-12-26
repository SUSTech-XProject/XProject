select
    msg.*,
    c.role_type, c.username, c.register_time, c.icon_url,
    h.role_type, h.username, h.register_time, h.icon_url
from messages as msg
  left join roles as c on msg.creator_role_id = c.role_id
  left join roles as h on msg.handler_role_id = h.role_id
where proj_id = 1 and msg.role_id = 1