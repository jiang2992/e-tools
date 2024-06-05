package org.jiang.tools.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    /**
     * 获取对象的所有属性(包括父类)
     *
     * @param obj 对象
     * @return 属性列表
     */
    public static List<Field> getAllFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                fields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }

    /**
     * 获取对象特定属性的值(包括父类)
     *
     * @param obj       对象
     * @param fieldName 属性名
     * @return 属性值
     * @throws NoSuchFieldException   属性不存在异常
     * @throws IllegalAccessException 属性访问异常
     */
    public static Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {
            try {
                Field field = currentClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException("No such field: " + fieldName);
    }

    /**
     * 设置对象特定属性的值(包括父类)
     *
     * @param obj       对象
     * @param fieldName 属性名
     * @param value     属性值
     * @throws NoSuchFieldException   属性不存在异常
     * @throws IllegalAccessException 属性访问异常
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {
            try {
                Field field = currentClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
                return;
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException("No such field: " + fieldName);
    }

}
