package me.devwckd.prestigerankup.util;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMultimap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.UUID;

import static me.devwckd.prestigerankup.util.ReflectionsUtils.*;

public class HeadUtils {

    private static final Class GAME_PROFILE_CLASS = getReflectionClass("com.mojang.authlib.GameProfile");
    private static final Class PROPERTY_MAP_CLASS = getReflectionClass("com.mojang.authlib.properties.PropertyMap");
    private static final Class PROPERTY_CLASS = getReflectionClass("com.mojang.authlib.properties.Property");
    private static final Constructor GAME_PROFILE_CONSTRUCTOR = getReflectionConstructor(GAME_PROFILE_CLASS, UUID.class, String.class);
    private static final Constructor PROPERTY_CONSTRUCTOR = getReflectionConstructor(PROPERTY_CLASS, String.class, String.class);
    private static final Method GET_PROPERTIES_METHOD = getReflectionDeclaredMethod(GAME_PROFILE_CLASS, "getProperties");
    private static final Method REMOVE_ALL_METHOD = getReflectionDeclaredMethod(ForwardingMultimap.class, "removeAll", Object.class);
    private static final Method PUT_METHOD = getReflectionMethod(PROPERTY_MAP_CLASS, "put", Object.class, Object.class);

    public static ItemStack injectSkin(ItemStack stack, String skin) {

        if (GAME_PROFILE_CONSTRUCTOR == null) throw new NullPointerException("gameProfileConstructor cannot be null!");
        if (GET_PROPERTIES_METHOD == null) throw new NullPointerException("getPropertiesMethod cannot be null!");
        if (REMOVE_ALL_METHOD == null) throw new NullPointerException("removeAllMethod cannot be null!");
        if (PROPERTY_CONSTRUCTOR == null) throw new NullPointerException("propertyConstructor cannot be null!");
        if (PUT_METHOD == null) throw new NullPointerException("putMethod cannot be null!");

        Object gameProfileObject = newReflectionInstance(GAME_PROFILE_CONSTRUCTOR, UUID.randomUUID(), null);
        if (gameProfileObject == null) throw new NullPointerException("gameProfileObject cannot be null!");

        Object propertyMapObject = invokeReflectionMethod(GET_PROPERTIES_METHOD, gameProfileObject);
        if (propertyMapObject == null) throw new NullPointerException("propertyMapObject cannot be null!");
        invokeReflectionMethod(REMOVE_ALL_METHOD, propertyMapObject, "textures");

        Object propertyObject = newReflectionInstance(PROPERTY_CONSTRUCTOR, "textures", skin);
        if (propertyObject == null) throw new NullPointerException("propertyObject cannot be null!");
        invokeReflectionMethod(PUT_METHOD, propertyMapObject, "textures", propertyObject);

        SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();
        Field profileField = getReflectionDeclaredField(skullMeta.getClass(), "profile");
        if (profileField == null) throw new NullPointerException("profileField cannot be null!");

        setReflectionFieldAccessible(profileField, true);
        setReflectionField(profileField, skullMeta, gameProfileObject);
        setReflectionFieldAccessible(profileField, false);
        stack.setItemMeta(skullMeta);

        return stack;

    }

}
