--�������ݿ�
create database seckill;
--ʹ�����ݿ�
use seckill;

--������ɱ��Ʒ��
create table seckill(
seckill_id bigint not null AUTO_INCREMENT   comment '��Ʒid' ,
`name` varchar(255) not null comment '��Ʒ����',
number int not null comment '��Ʒ���',
start_time timestamp not null comment '��ʼʱ��',
end_time timestamp not null comment '����ʱ��',
create_time timestamp not null default  current_timestamp comment '����ʱ��',
primary key(seckill_id),
key index_start_time(start_time),
key index_end_time(end_time),
key index_create_time(create_time)
) engine=innodb auto_increment=1000 default charset=utf8 comment '��ɱ��Ʒ��';

--������������ʱ�������������޸�
alter table seckill change seckill_id seckill_id bigint not null AUTO_INCREMENT;

--�����ɹ���ɱ����Ʒ��
create table success_killed(
seckill_id bigint not null comment '��ɱ��Ʒid',
user_phone bigint not null comment '�û��绰',
state tinyint not null default -1 comment '״̬��-1��Ч,0��Ч,1�Ѹ���',
create_time timestamp not null default current_timestamp comment '����ʱ��',
primary key(seckill_id,user_phone),
key idx_create_time(create_time)
) engine=innodb  default charset=utf8 comment '�ɹ���ɱ��Ʒ��';

--������
insert into  seckill(`name`,number,start_time,end_time)
values
('1000Ԫ��ɱiphone',100,'2018-03-27 19:00','2018-04-07 19:00'),
('100Ԫ��ɱipad',100,'2018-03-27 19:00','2018-04-07 19:00'),
('10Ԫ��ɱiwatch',100,'2018-03-27 19:00','2018-04-07 19:00'),
('1Ԫ��ɱiphone x',1,'2018-03-27 19:00','2018-04-07 19:00')