package org.seckill.entity;

import java.util.Date;

/*
�ɹ���ɱ��Ʒ��

 */
public class SuccessKilled {
    private long seckillId; // ��Ʒid
    private long userPhone; //�û��绰
    private Date createTime; // ����ʱ��
    private short state; // ״̬
    private  Seckill seckill; //��ɱ����Ʒ����

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", createTime=" + createTime +
                ", state=" + state +
                ", seckill=" + seckill +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }
}
