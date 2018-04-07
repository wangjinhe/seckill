package org.seckill.dto;

/**
 * ��ɱ��ַ��¶Dto
 */
public class Exposer {
    /**
     * ��ƷId
     */
    private  long seckillId;
    /**
     * ��������ǰʱ�� ����
     */
    private  long now;
    /**
     * ��ɱ��ʼʱ��
     */
    private  long start;
    /**
     * ��ɱ����ʱ��
     */
    private  long end;
    /**
     * ��ɱ��ַ�Ƿ�¶
     */
    private  boolean exposed;
    /**
     * ��ɱ��ַ���ܲ���md5
     */
    private  String md5;

    /**
     * ��ɱû��ʼ�Ĺ��캯��
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
     * ��ɱ��ʼʱ�Ĺ��캯��
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
     * ��ѯ������ɱ��Ʒʱ�������������
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
