create table test
(
	id int not null,
	username varchar(30) null comment '用户名',
    sex int null comment '性别 0:男,1:女',
	status int null comment '状态 1:有效,2:无效;',
	constraint table_name_pk
		primary key (id)
);

INSERT INTO test (id, username,sex, status) VALUES (1,'maple',0, 1);
INSERT INTO test (id, username,sex, status) VALUES (2, 'admin',1, 0);
