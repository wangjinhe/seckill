package org.seckill.Util;


import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
封装Protostuff序列化与反序列化
参考：https://blog.csdn.net/wangfei8348/article/details/60140301
 */
public class ProtostuffHelper {


    // 保存schema，由于获取schema比较耗时，因此第1次获取后，保存到Map中
    // ConcurrentHashMap用法参考http://www.importnew.com/26049.html 线程安全的hash
    // HashMap线上不安全参考：http://pettyandydog.com/2016/08/28/HashMap_infinite_loop/#more
    // Class<?>  ?是通配符，代表任意的,Class<?>代表任意的类
    private  static Map<Class<?>,Schema<?>> cachedSchema = new ConcurrentHashMap();

    // 根据类类型获取对应的schema
    // Class<T> cls 代表一种类类型，传参时需要指定具体的类型
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if(schema == null) {
           schema =  RuntimeSchema.createFrom(cls);
           if(schema != null) {
               cachedSchema.put(cls,schema);
           }
        }
        return  schema;
    }

    /**
     * 序列化  泛型对象 》 数组
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte [] Serialize(T obj){

        Class<T> cls = (Class<T>) obj.getClass(); // 根据对象获取类类型
        Schema schema = getSchema(cls); // 根据类类型，获取schema
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE); // 分配默认的缓冲区
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception ex) {
            throw  new IllegalStateException(ex.getMessage(),ex);
        } finally {
            buffer.clear();// 释放空间
        }
    }

    /**
     * 反序列化
     * @param bytes
     * @param cls
     * @param <T>
     * @return
     */
    public  static <T> T Deserialize(byte [] bytes,Class<T> cls){
        try {
            //T message = (T) cls.newInstance();
            Schema<T> schema = getSchema(cls);
            T message = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            return  message;
        } catch (Exception ex) {
            throw  new IllegalStateException(ex.getMessage(),ex);
        }
    }
}
