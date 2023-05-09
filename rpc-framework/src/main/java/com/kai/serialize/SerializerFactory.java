package com.kai.serialize;

import com.kai.serialize.impl.JdkSerializer;

import java.util.HashSet;
import java.util.Set;

/**
 * 序列化器工厂，用于创建对应的序列化器
 *
 * @author wangkaiping
 */
public class SerializerFactory {

    public static final byte JDK_SERIALIZER = 0;

    private static final Set<Byte> SERIALIZERS = new HashSet<Byte>(){{
        add(JDK_SERIALIZER);
    }};

    private SerializerFactory(){

    }

    public static boolean containsSerializer(byte serializerId) {
        return SERIALIZERS.contains(serializerId);
    }

    public static Serializer createSerializer(byte serializerType) {
        switch (serializerType){
            case JDK_SERIALIZER:
                return new JdkSerializer();
            default:
                return null;
        }
    }
}
