--创建数据库
create database seckill;
--使用数据库
use seckill;

--创建秒杀商品表
create table seckill(
seckill_id bigint not null AUTO_INCREMENT   comment '商品id' ,
`name` varchar(255) not null comment '商品标题',
number int not null comment '商品库存',
start_time timestamp not null comment '开始时间',
end_time timestamp not null comment '结束时间',
create_time timestamp not null default  current_timestamp comment '创建时间',
primary key(seckill_id),
key index_start_time(start_time),
key index_end_time(end_time),
key index_create_time(create_time)
) engine=innodb auto_increment=1000 default charset=utf8 comment '秒杀商品表';

--忘记自增增长时，用下面的语句修改
alter table seckill change seckill_id seckill_id bigint not null AUTO_INCREMENT;

--创建成功秒杀的商品表
create table success_killed(
seckill_id bigint not null comment '秒杀商品id',
user_phone bigint not null comment '用户电话',
state tinyint not null default -1 comment '状态：-1无效,0有效,1已付款',
create_time timestamp not null default current_timestamp comment '创建时间',
primary key(seckill_id,user_phone),
key idx_create_time(create_time)
) engine=innodb  default charset=utf8 comment '成功秒杀商品表';

--造数据
insert into  seckill(`name`,number,start_time,end_time)
values
('1000元秒杀iphone',100,'2018-03-27 19:00','2018-04-07 19:00'),
('100元秒杀ipad',100,'2018-03-27 19:00','2018-04-07 19:00'),
('10元秒杀iwatch',100,'2018-03-27 19:00','2018-04-07 19:00'),
('1元秒杀iphone x',1,'2018-03-27 19:00','2018-04-07 19:00')