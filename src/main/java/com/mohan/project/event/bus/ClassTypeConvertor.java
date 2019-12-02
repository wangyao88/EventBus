package com.mohan.project.event.bus;

/**
 * @author mohan
 */
public class ClassTypeConvertor {

    public static Class<?> convert(Class<?> clazz) {
        if(clazz == null) {
            return null;
        }
        String typeName = clazz.getTypeName();
        switch (typeName) {
            case "java.lang.Byte":
                return byte.class;
            case "java.lang.Short":
                return short.class;
            case "java.lang.Character":
                return char.class;
            case "java.lang.Integer":
                return int.class;
            case "java.lang.Long":
                return long.class;
            case "java.lang.Float":
                return float.class;
            case "java.lang.Double":
                return double.class;
            default:
                return clazz;
        }
    }

    public static void main(String[] args) {
        System.out.println(convert(new Integer(1).getClass()));
    }
}
