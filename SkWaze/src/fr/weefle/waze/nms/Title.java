package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

public interface Title {
    public void sendTitle(Player p, String title, String subtitle, int time) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException;
}
