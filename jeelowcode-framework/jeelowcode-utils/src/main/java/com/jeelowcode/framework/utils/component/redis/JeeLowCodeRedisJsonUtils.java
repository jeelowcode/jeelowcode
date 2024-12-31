/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.component.redis;

/**
 * @author JX
 * @create 2024-03-05 11:42
 * @dedescription:
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;


public class JeeLowCodeRedisJsonUtils {
    public static final ObjectMapper OBJ_MAPPER = new ObjectMapper();


    /**
     * 普通对象之间类型的转化
     *
     * @param source 原对象
     * @param target 目标类型
     * @param <T>    目标参数类型
     * @return object after transformation
     */
    public static <T> T objParse(Object source, Class<T> target) {
        try {
            if (source.getClass().equals(target)) {
                return OBJ_MAPPER.convertValue(source, target);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 普通列表之间类型的转化
     *
     * @param source 原列表
     * @param target 目标列表类型
     * @param <T>    目标列表参数类型
     * @return list after transformation
     */
    public static <S, T> List<T> listParse(List<S> source, Class<T> target) {
        try {
            return OBJ_MAPPER.convertValue(source, OBJ_MAPPER.getTypeFactory().constructCollectionType(List.class, target));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 普通哈希列表之间类型的转化
     *
     * @param source    原哈希列表
     * @param keyType   键类型
     * @param valueType 值类型
     * @param <SK>      原键类型
     * @param <SV>      原值类型
     * @param <TK>      目标键类型
     * @param <TV>      目标值类型
     * @return map after transformation
     */
    public static <SK, SV, TK, TV> Map<TK, TV> mapParse(Map<SK, SV> source, Class<TK> keyType, Class<TV> valueType) {
        try {
            return OBJ_MAPPER.convertValue(source, OBJ_MAPPER.getTypeFactory().constructMapType(Map.class, keyType, valueType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换成json字符串(序列化)
     *
     * @param obj 原对象
     * @return string after serialized object
     */
    public static String objToJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJ_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 将json转化为对象(反序列化)
     *
     * @param source 原对象json
     * @param target 目标类型
     * @param <T>    目标类参数类型
     * @return deserialized object
     */
    public static <T> T jsonToObj(String source, Class<T> target) {
        OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return OBJ_MAPPER.readValue(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将列表json转化为对象集合
     *
     * @param source 原对象json
     * @param target 目标类型
     * @param <T>    目标类参数类型
     * @return deserialized object collection
     */
    public static <T> List<T> jsonToList(String source, Class<T> target) {
        try {
            return OBJ_MAPPER.readValue(source, OBJ_MAPPER.getTypeFactory().constructCollectionType(List.class, target));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将哈希表json转化为对象集合
     *
     * @param source    原对象json
     * @param keyType   键类型
     * @param valueType 值类型
     * @param <K>       键参数类型
     * @param <V>       值参数类型
     * @return deserialized map collection
     */
    public static <K, V> Map<K, V> jsonToMap(String source, Class<K> keyType, Class<V> valueType) {
        try {
            return OBJ_MAPPER.readValue(source, OBJ_MAPPER.getTypeFactory().constructMapType(Map.class, keyType, valueType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}



