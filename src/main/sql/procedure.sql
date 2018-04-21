-- ����洢����  ִ����ɱ
-- ִ����ɱ�߼�  1,�������� 2,������ϸ 3,����� 4,�ύ����
delimiter $$  -- ��������ָ���������mysql ������������,����ִ����

-- ���� in ������������� out �����������
-- row_count���������޸ĵ�����

-- ���ؽ������  1:��ɱ�ɹ�  0:��ɱ����  -1:�ظ���ɱ  -2:ϵͳ�쳣   ��ö��SeckillStateEnum��״̬��Ӧ
create procedure execute_seckill(in v_seckill_id bigint,in v_phone bigint,in v_kill_time timestamp,out v_result int)
  begin
    declare insert_count int default 0; -- ��������������޸ĵ�����
    start transaction;  -- ��������
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

--�洢���̶������

delimiter ;  -- �ָ� �ֺŷָ���

set @v_result = 0;
-- ִ�д洢����
call execute_seckill(1001,18711111111,now(),@v_result)
select @v_result
