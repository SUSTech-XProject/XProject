drop table if exists test_table;

create table test_table
(
    id  int,
    str varchar,
    num int
);

insert into test_table values(1, 'this is a string', 1);