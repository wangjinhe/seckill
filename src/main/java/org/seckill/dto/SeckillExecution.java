package org.seckill.dto;

import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

/**
 * ��ɱִ�н��dto
 */
public class SeckillExecution {
    private  long seckillId; // ��ɱ��ƷId
    private  int state; // ��ɱ״̬
    private  String stateInfo; // ��ɱ״̬��Ϣ
    private SuccessKilled successKilled; // ��ɱ�ɹ�����Ʒ


    /**
     * ��ɱ�ɹ��Ĺ��캯��
     * @param seckillId
     * @param seckillStatEnum
     * @param successKilled
     */
    public SeckillExecution(long seckillId, SeckillStatEnum seckillStatEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    /**
     * ��ɱʧ�ܵĹ��캯��
     * @param seckillId
     * @param seckillStatEnum
     */
    public SeckillExecution(long seckillId,SeckillStatEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
