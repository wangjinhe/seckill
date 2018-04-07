package org.seckill.dto;

/*
所有ajax范围的内容，都使用这个dto
使用泛型方便返回各种数据
成功返回数据；错误返回错误信息
 */
public class SeckillResult <T> {
    private boolean success;

    private T data;

    private String error;

    /**
     * 成功时返回正确的json数据
     * @param success
     * @param data
     */
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 错误时，返回错误信息
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
