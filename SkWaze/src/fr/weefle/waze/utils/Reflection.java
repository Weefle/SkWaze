package fr.weefle.waze.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflection {
	
public Class< ? > getNMSClass ( String classname )
	{
	    String version = Bukkit.getServer ( ).getClass ( ).getPackage ( ).getName ( ).replace ( ".", "," ).split ( "," )[ 3 ] + ".";
	    String name = "net.minecraft.server." + version + classname;
	    Class< ? > nmsClass = null;
	    try
	    {
	        nmsClass = Class.forName ( name );
	    } catch ( ClassNotFoundException e )
	    {
	        e.printStackTrace ( );
	    }
	    return nmsClass;
	}

	public Object getConnection ( Player player ) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InvocationTargetException
	{
	    Method getHandle = player.getClass ( ).getMethod ( "getHandle" );
	    Object nmsPlayer = getHandle.invoke ( player );
	    Field conField = nmsPlayer.getClass ( ).getField ( "playerConnection" );
	    Object con = conField.get ( nmsPlayer );
	    return con;
	}

}
