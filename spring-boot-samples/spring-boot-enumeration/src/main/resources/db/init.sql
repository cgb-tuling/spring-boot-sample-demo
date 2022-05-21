create table table_name
(
	id int not null,
	username varchar(30) null comment '用户名',
	status int null comment '状态 1:有效,2:无效;',
	constraint table_name_pk
		primary key (id)
);

INSERT INTO test (id, username, status) VALUES (1, 'maple', 1);
INSERT INTO test (id, username, status) VALUES (2, 'admin', 0);
