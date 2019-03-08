-- -----------------------
-- 用户表
-- -----------------------
drop table if exists operator;
create table operator(
  uid               int(11)           not null auto_increment         comment '用户ID',
  login_name        varchar(128)      not null                        comment '用户姓名'
) engine=innodb auto_increment=200 default charset=utf8 comment = '用户表';