package me.devwckd.prestigerankup.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectionsUtils {

    public static Class getReflectionClass(String path) {
        try {
            return Class.forName(path);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to get nms class " + path + ".");
            return null;
        }
    }

    public static Constructor getReflectionConstructor(Class clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to get constructor for class " + clazz.getName() +
                    " [ " + Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining(", ")) + "].");
            return null;
        }
    }

    public static Object newReflectionInstance(Constructor constructor, Object... constructorParams) {
        try {
            return constructor.newInstance(constructorParams);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to instantiate object for " + constructor.getName() + ".");
            return null;
        }
    }

    public static Method getReflectionDeclaredMethod(Class clazz, String methodName, Class... parameters) {
        try {
            return clazz.getDeclaredMethod(methodName, parameters);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to get method " + methodName + " on class " + clazz.getName() + ".");
            return null;
        }
    }

    public static Method getReflectionMethod(Class clazz, String methodName, Class... parameters) {
        try {
            return clazz.getMethod(methodName, parameters);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to get method " + methodName + " on class " + clazz.getName() + ".");
            return null;
        }
    }

    public static Object invokeReflectionMethod(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to invoke method " + method.getName() + ".");
            return null;
        }
    }

    public static Field getReflectionDeclaredField(Class clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed get field " + fieldName + " in " + clazz.getName() + ".");
            return null;
        }
    }

    public static void setReflectionFieldAccessible(Field field, boolean accessible) {
        try {
            field.setAccessible(accessible);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to modify accessibility for " + field.getName() + ".");
        }
    }

    public static void setReflectionField(Field field, Object object, Object value) {
        try {
            field.set(object, value);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Failed to modify field " + field.getName() + "'s value.");
        }
    }

}
