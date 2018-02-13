package com.magic.daoyuan.business.redis.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.*;

/**
 * Created by Eric Xie on 2017/9/1 0001.
 */
public class SerializeUtil {


    /**
     * 序列化对象
     *
     * @param object
     */
    protected static <T extends Serializable> byte[] serialize(T object) {
        byte[] b = null;
        ByteArrayOutputStream baos = null;
        try {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            kryo.setRegistrationRequired(false);
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            kryo.register(object.getClass());
            baos = new ByteArrayOutputStream();
            Output output = new Output(baos);
            kryo.writeClassAndObject(output, object);
            output.flush();
            output.close();
            b = baos.toByteArray();
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != baos){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return b;
    }

    protected static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        Object obj = null;
        try {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            bais = new ByteArrayInputStream(bytes);
            Input input = new Input(bais);
            obj =  kryo.readClassAndObject(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != bais){
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}
