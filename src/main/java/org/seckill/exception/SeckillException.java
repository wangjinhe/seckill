package org.seckill.exception;

/**
 * ��ɱ�쳣��
 */
public class SeckillException  extends  RuntimeException {

    public  SeckillException(String message){
        super(message);
    }
    public  SeckillException(String message,Throwable cause){
        super(message,cause);
    }
}
