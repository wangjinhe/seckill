package org.seckill.Util;


import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
��װProtostuff���л��뷴���л�
�ο���https://blog.csdn.net/wangfei8348/article/details/60140301
 */
public class ProtostuffHelper {


    // ����schema�����ڻ�ȡschema�ȽϺ�ʱ����˵�1�λ�ȡ�󣬱��浽Map��
    // ConcurrentHashMap�÷��ο�http://www.importnew.com/26049.html �̰߳�ȫ��hash
    // HashMap���ϲ���ȫ�ο���http://pettyandydog.com/2016/08/28/HashMap_infinite_loop/#more
    // Class<?>  ?��ͨ��������������,Class<?>�����������
    private  static Map<Class<?>,Schema<?>> cachedSchema = new ConcurrentHashMap();

    // ���������ͻ�ȡ��Ӧ��schema
    // Class<T> cls ����һ�������ͣ�����ʱ��Ҫָ�����������
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
     * ���л�  ���Ͷ��� �� ����
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte [] Serialize(T obj){

        Class<T> cls = (Class<T>) obj.getClass(); // ���ݶ����ȡ������
        Schema schema = getSchema(cls); // ���������ͣ���ȡschema
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE); // ����Ĭ�ϵĻ�����
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception ex) {
            throw  new IllegalStateException(ex.getMessage(),ex);
        } finally {
            buffer.clear();// �ͷſռ�
        }
    }

    /**
     * �����л�
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
