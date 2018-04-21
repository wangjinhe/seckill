-- 定义存储过程  执行秒杀
-- 执行秒杀逻辑  1,开启事务 2,插入明细 3,减库存 4,提交事务
delimiter $$  -- 定义命令分隔符，告诉mysql 这个命令结束了,可以执行了

-- 参数 in 代表输入参数； out 代表输出参数
-- row_count函数返回修改的条数

-- 返回结果意义  1:秒杀成功  0:秒杀结束  -1:重复秒杀  -2:系统异常   和枚举SeckillStateEnum的状态对应
create procedure execute_seckill(in v_seckill_id bigint,in v_phone bigint,in v_kill_time timestamp,out v_result int)
  begin
    declare insert_count int default 0; -- 定义变量，保存修改的行数
    start transaction;  -- 开启事务
        insert ignore into success_killed(seckill_id,user_phone,state,create_time)
        values(v_seckill_id,v_phone,0,v_kill_time);
        select row_count() into insert_count;
        if(insert_count = 0) then
            rollback;
            set v_result = -1;
        elseif(insert_count < 0) then
            rollback;
            set v_result = -2;
        else
            update seckill set number=number-1
            where seckill_id=v_seckill_id
            and  number>0
            and start_time  < v_kill_time
            and end_time >  v_kill_time;

            select row_count() into insert_count;
                if(insert_count = 0) then
                    rollback;
                    set v_result = 0;
                elseif(insert_count < 0) then
                    rollback;
                    set v_result = -2;
                else
                    commit;
                    set v_result = 1;
                end if;
        end if;
  end
$$

--存储过程定义结束

delimiter ;  -- 恢复 分号分隔符

set @v_result = 0;
-- 执行存储过程
call execute_seckill(1001,18711111111,now(),@v_result)
select @v_result
