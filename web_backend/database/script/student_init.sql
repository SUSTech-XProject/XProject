update students
set (flags, skills, bio) = ('["Hardworking","No DDL","Good coder","No stay up late"]',
                            '["Java","String boot","Vue"]',
                            'I have a great plan, but there isn''t enough room here.')
where std_id = 1;

update students
set (flags, skills, bio) = ('["DDL fighter", "Database expert"]',
                            '["Mysql","Project management"]',
                            'I have a great plan, but there isn''t enough room here.')
where std_id = 2;