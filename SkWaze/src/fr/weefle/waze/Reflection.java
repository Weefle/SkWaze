package fr.weefle.waze;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

public class Reflection
{
  public static Object newFromNMS(String nms)
  {
    try
    {
      return nmsClass(nms).newInstance();
    }
    catch (InstantiationException|IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static Object newFromOBC(String obc)
  {
    try
    {
      return obcClass(obc).newInstance();
    }
    catch (InstantiationException|IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static Class<?> nmsClass(String nms)
  {
    try
    {
      return Class.forName("net.minecraft.server." + getServerVersion() + "." + nms);
    }
    catch (ClassNotFoundException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static Class<?> obcClass(String obc)
  {
    try
    {
      return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + "." + obc);
    }
    catch (ClassNotFoundException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public static Object getHandle(Object obj)
  {
    try
    {
      return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Field getField(Class<?> clazz, String name)
  {
    try
    {
      Field field = clazz.getDeclaredField(name);
      field.setAccessible(true);
      return field;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Method getMethod(Class<?> clazz, String name, Class<?>... args)
  {
    Method[] arrayOfMethod;
    int j = (arrayOfMethod = clazz.getMethods()).length;
    for (int i = 0; i < j; i++)
    {
      Method m = arrayOfMethod[i];
      if ((m.getName().equals(name)) && ((args.length == 0) || (classesEqual(args, m.getParameterTypes()))))
      {
        m.setAccessible(true);
        return m;
      }
    }
    return null;
  }
  
  public static boolean classesEqual(Class<?>[] l1, Class<?>[] l2)
  {
    boolean equal = true;
    if (l1.length != l2.length) {
      return false;
    }
    for (int i = 0; i < l1.length; i++) {
      if (l1[i] != l2[i])
      {
        equal = false;
        break;
      }
    }
    return equal;
  }
  
  public static void sendPacket(Object packet, Player... players)
  {
    try
    {
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = players).length;
      for (int i = 0; i < j; i++)
      {
        Player p = arrayOfPlayer[i];
        Object craftPlayer = getHandle(p);
        Object connection = getField(craftPlayer.getClass(), "playerConnection").get(craftPlayer);
        getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, new Object[] { packet });
      }
    }
    catch (IllegalAccessException|InvocationTargetException e)
    {
      e.printStackTrace();
    }
  }
  
  public static String getServerVersion()
  {
    return org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", "@").split("@")[3];
  }
  
  public static Class<?> getCaller()
  {
    try
    {
      return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName(), false, Reflection.class.getClassLoader());
    }
    catch (ClassNotFoundException e) {}
    return null;
  }
}