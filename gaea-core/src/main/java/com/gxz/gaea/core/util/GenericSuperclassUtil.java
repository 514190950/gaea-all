package com.gxz.gaea.core.util;

import org.springframework.cglib.core.ClassInfo;
import org.springframework.cglib.core.ReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericSuperclassUtil {

    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entityClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entityClass = (Class<?>) actualTypeArguments[0];
            }
        }

        return entityClass;
    }

    /***
     * 获取父类的泛型内容 默认获取第一个
     * @param clazz 子类Class
     * @return 如果是null说明没有泛型，如果有多个泛型默认返回第一个 如果需要多个泛型 请使用重载方法
     **/
    public static Class<?> getGenericSuperclass(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((Class<?>[]) ((ParameterizedType) genericSuperclass).getActualTypeArguments())[0];
        }
        return null;
    }


    /**
     * 获取指定父类的多个泛型
     * @param clazz 子类class
     * @param targetSuperClass 指定父类的多个泛型 如果是null将返回直接父类
     * @return 返回执行父类的多个泛型集合 如果是null说明子类没有继承指定父类
     **/
    public static Class<?>[] getAllGenericSuperClass(Class<?> clazz, Class<?> targetSuperClass) throws ClassNotFoundException {
        if (clazz == Object.class) {
            return null;
        }
        Type superType = clazz.getGenericSuperclass();
        while (targetSuperClass != null && superType != null && !targetSuperClass.getName().equals(getRealityClassName(superType))) {
            superType = Class.forName(getRealityClassName(superType)).getGenericSuperclass();
        }
        if (superType instanceof ParameterizedType) {
            return (Class<?>[]) ((ParameterizedType) superType).getActualTypeArguments();
        }
        return null;
    }


    /***
     * 获得父类的目标泛型
     * 例子： Class A extend B<String,Integer>
     * getAllGenericSuperClass(A.class,B.class,1)  return ---> Integer.class
     * @param clazz 子类
     * @param targetSuperClass 指定父类
     * @param index 第几个泛型
     * @return 返回父类的第几个泛型
     **/
    public static Class<?> getAllGenericSuperClass(Class<?> clazz, Class<?> targetSuperClass, int index) throws ClassNotFoundException {
        Class<?>[] allGenericSuperClass = getAllGenericSuperClass(clazz, targetSuperClass);
        if (allGenericSuperClass == null) {
            return null;
        }
        return allGenericSuperClass[index];
    }


    /***
     * 从Type中获得真实的Class名
     * @param type type
     * @return Class真实名称
     **/
    private static String getRealityClassName(Type type) {
        if (type instanceof Class) {
            return type.getTypeName();
        }
        String typeName = type.getTypeName();
        StringBuilder builder = new StringBuilder();
        for (char c : typeName.toCharArray()) {
            if (c == '<') {
                break;
            }
            builder.append(c);
        }
        return builder.toString();
    }


}
