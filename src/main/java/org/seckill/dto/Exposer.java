package org.seckill.dto;

/**
 * 秒杀地址暴露Dto
 */
public class Exposer {
    /**
     * 商品Id
     */
    private  long seckillId;
    /**
     * 服务器当前时间 毫秒
     */
    private  long now;
    /**
     * 秒杀开始时间
     */
    private  long start;
    /**
     * 秒杀结束时间
     */
    private  long end;
    /**
     * 秒杀地址是否暴露
     */
    private  boolean exposed;
    /**
     * 秒杀地址加密参数md5
     */
    private  String md5;

    /**
     * 秒杀没开始的构造函数
     * @param seckillId
     * @param now
     * @param start
     * @param end
     * @param exposed
     * @param md5
     */
    public Exposer(long seckillId, long now, long start, long end, boolean exposed, String md5) {
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
        this.exposed = exposed;
        this.md5 = md5;
    }

    /**
     * 秒杀开始时的构造函数
     * @param seckillId
     * @param exposed
     * @param md5
     */
    public Exposer(long seckillId, boolean exposed, String md5) {
        this.seckillId = seckillId;
        this.exposed = exposed;
        this.md5 = md5;
    }

    /**
     * 查询不到秒杀商品时，返回这个对象
     * @param seckillId
     * @param exposed
     */
    public Exposer(long seckillId, boolean exposed) {
        this.seckillId = seckillId;
        this.exposed = exposed;
    }


    @Override
    public String toString() {
        return "Exposer{" +
                "seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                ", exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                '}';
    }


    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
