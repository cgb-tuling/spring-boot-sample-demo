-- 当存储过程`p`存在时，删除。$$$ 为分隔符
drop procedure if exists p;$$$

-- 创建存储过程`p`
create procedure p()
begin
  declare row_num int;
select count(*) into row_num from `tb_user_copy`;
if row_num = 0 then
    INSERT INTO `tb_user_copy`(`username`, `password`) VALUES ('zhangsan', '123456');
end if;
end;$$$

-- 调用存储过程`p`
call p();$$$