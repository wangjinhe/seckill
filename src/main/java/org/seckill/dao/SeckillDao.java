package org.seckill.dao;


import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

//@Component ���ע���ǲ���Ҫ��
public interface SeckillDao {

    /**
     * ������ƷId,��ѯ��ɱ��Ʒ
     * @param seckillId ��Ʒid
     * @return ��ѯ������ɱ��Ʒ
     */
    Seckill queryById(long seckillId);

    /**
     *  ��ѯ��������ɱ��Ʒ
     * @param offset ƫ����
     * @param count ����
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset ,@Param("count") int count);

    /**
     * �����
     * @param seckillId ��ƷId
     * @param killTime �μ���ɱʱ��
     * @return �����޸ĵ���������
     //ע�⣺@Param��ʾ���������������ǲ��ӣ�����javaû�б����βε�ϰ�ԣ���ֵ��junit���Իᱨ��
    //ԭ�򣺶�Ӧ��xml�ļ���sql���Ҫ����������������Ҫ��ȷ���Σ�����Ҫ����������
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime")  Date killTime);


    /**
     * ͨ���洢����ִ����ɱ
     * ͨ��map���ݲ���
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);



}
