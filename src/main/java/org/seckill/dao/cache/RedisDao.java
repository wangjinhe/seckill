package org.seckill.dao.cache;

import org.seckill.Util.ProtostuffHelper;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



/*
  redis������
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private  final JedisPool jedisPool;

    /**
     * ����ip�Ͷ˿ںų�ʼ��jedisPool
     * @param ip
     * @param port
     */
    public  RedisDao(String ip,int port)
    {
        jedisPool = new JedisPool(ip,port);
    }

    /**
     * ����seckillId��ȡ��ɱ����
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(Long seckillId) {
        try {
            String key = "seckill:" + seckillId;
            Jedis jedis = jedisPool.getResource();
            try {
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    Seckill seckill = ProtostuffHelper.Deserialize(bytes, Seckill.class);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return  null;
    }

    /**
     * ����ɱ������뵽redis��
     * @param seckill
     */
    public  String putSeckill(Seckill seckill) {
        try {
            if (seckill == null) {
                return "seckill is null";
            }
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] data = ProtostuffHelper.Serialize(seckill);
                int  timeout = 60 * 60; // ����һ��Сʱ��ʱ����λ����
                String result = jedis.setex(key.getBytes(),timeout, data);
                return  result;
            } finally {
                jedis.close();
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return  null;
    }


}
