package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * ��Ʒ��ɱ�ķ�����

 * ҵ��ӿڣ�վ��"ʹ����"�Ƕ���ƽӿ�
 *
 * �������棺
 * 1.�����������ȣ����������Ҫ�ǳ����
 * 2.������ҪԽ����Խ��
 * 3.��������(return ����һ��Ҫ�Ѻ�/����return�쳣������������쳣)
 *
 */
public interface SeckillService {

    //��ѯһ����ɱ��Ʒ
    Seckill queryById(long seckillId);

    // ��ѯ��ɱ��Ʒ�б�
    List<Seckill> getSeckillList(int offset,int count);

    /**
     * ������ɱ��ַ
     * ����ɱ��ʼʱ������ɱ��ַ
     * ����ɱû�п�ʼʱ������ϵͳʱ�����ɱʱ��
     * @param seckillId
     * @return
     */
    Exposer getExposerUrl(long seckillId);

    /**
     * ִ����ɱ���п���ʧ�ܣ���˰��쳣�׳���
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
    throws SeckillException,RepeatKillException,SeckillCloseException;


    /**
     * ִ����ɱ���п���ʧ�ܣ���˰��쳣�׳���
     * ͨ���洢���̵���
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
