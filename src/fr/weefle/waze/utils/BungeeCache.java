package fr.weefle.waze.utils;

import fr.weefle.waze.Waze;
import org.bukkit.Bukkit;

public class BungeeCache {

    public int onlineGlobal;
    public String serverList;

    public BungeeCache(Waze main) {

        Bukkit.getScheduler().runTaskTimerAsynchronously(main, () -> {


            /*Waze.getComApi().sendRequest(new PluginMessageRequest("SkWrapper-online-count-global") {

                @Override
                public void onAnswer(PluginMessage response) {

                    onlineGlobal = response.getDataAsInt("global-count");

                }
            });


            Waze.getComApi().sendRequest(new PluginMessageRequest("SkWrapper-get-servers") {

                @Override
                public void onAnswer(PluginMessage response) {

                    serverList = response.getData("server-list");

                }
            });*/



            /*PacketGetOnlineCountGlobal onlineCount = new PacketGetOnlineCountGlobal();
            Object obj = onlineCount.send();
            onlineGlobal = (int) obj;

            PacketGetServers serverGlobal = new PacketGetServers();
            Object obj1 = serverGlobal.send();
            serverList = (String) obj1;*/


        }, 20L, 20L);

    }

    public int getOnlineCountGlobal() {
        return onlineGlobal;
    }

    public String getServerList() {
        return serverList;
    }

}