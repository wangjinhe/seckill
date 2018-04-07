package org.seckill.dto;

/*
����ajax��Χ�����ݣ���ʹ�����dto
ʹ�÷��ͷ��㷵�ظ�������
�ɹ��������ݣ����󷵻ش�����Ϣ
 */
public class SeckillResult <T> {
    private boolean success;

    private T data;

    private String error;

    /**
     * �ɹ�ʱ������ȷ��json����
     * @param success
     * @param data
     */
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * ����ʱ�����ش�����Ϣ
     * @param success
     * @param error
     */
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
