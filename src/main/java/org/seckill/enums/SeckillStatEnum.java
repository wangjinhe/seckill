package org.seckill.enums;


/*
��ɱ״̬ö����
�������ֵ��ŵ�ö������
 */
public enum SeckillStatEnum {
    SUCCESS(1,"��ɱ�ɹ�"),
    END(0,"��ɱ����"),
    // �쳣��ͨ���ø���
    REPEAT_KILL(-1,"�ظ���ɱ"),
    INNER_ERROR(-2,"ϵͳ����"),
    DATA_REWRITE(-3,"���ݴ۸�")
    ;

    private  int state;// ��ɱ״̬
    private  String stateInfo; // ��ɱ״̬��ʾ
    /**
     * ö����Ĺ��캯��
     * @param state
     * @param stateInfo
     */
    SeckillStatEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    /**
     * ������ɱ״̬����ö��ֵ
     * values()�����᷵�����е�ö��ֵ
     * @param state
     * @return
     */
    public static  SeckillStatEnum stateOf(int state){
        for (SeckillStatEnum seckillState: values()) {
            if(seckillState.getState() == state){
                return  seckillState;
            }
        }
        return  null;
    }


    public int getState() {
        return state;
    }


    public String getStateInfo() {
        return stateInfo;
    }


}
