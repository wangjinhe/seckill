package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * �ɹ���ɱDao
 */
public interface SuccessKilledDao {
    /**
     * ������ɱ�ɹ�����Ʒ��ϸ
     * @param seckillId ��ƷId
     * @param userPhone �û��ֻ�
     * @return ����ɹ�����1��ʧ�ܷ���0
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * ��ѯ��ɱ�ɹ�����Ʒ
     * @param seckillId ��ƷId
     * @param userPhone �û��ֻ���
     * @return ������ɱ�ɹ�����Ʒ
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
