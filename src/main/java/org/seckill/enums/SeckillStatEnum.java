package org.seckill.enums;


/*
秒杀状态枚举类
将数据字典存放到枚举类中
 */
public enum SeckillStatEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    // 异常的通常用负数
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统错误"),
    DATA_REWRITE(-3,"数据篡改")
    ;

    private  int state;// 秒杀状态
    private  String stateInfo; // 秒杀状态提示
    /**
     * 枚举类的构造函数
     * @param state
     * @param stateInfo
     */
    SeckillStatEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    /**
     * 根据秒杀状态返回枚举值
     * values()方法会返回所有的枚举值
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
