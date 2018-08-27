package fr.weefle.waze.utils;

import org.bukkit.Bukkit;
import fr.weefle.waze.Waze;
import fr.weefle.waze.nms.ActionBarAPI;
import fr.weefle.waze.nms.ActionBarNew;
import fr.weefle.waze.nms.AutoRespawnAPI;
import fr.weefle.waze.nms.AutoRespawnNew;
import fr.weefle.waze.nms.BossBarAPI;
import fr.weefle.waze.nms.BossBarNew;
import fr.weefle.waze.nms.Nametag;
import fr.weefle.waze.nms.ParticleAPI;
import fr.weefle.waze.nms.ParticleNew;
import fr.weefle.waze.nms.Ping;
import fr.weefle.waze.nms.ScoreBoard;
import fr.weefle.waze.nms.Tablist;
import fr.weefle.waze.nms.Title;
import fr.weefle.waze.old.ActionBarOld;
import fr.weefle.waze.old.AutoRespawnOld;
import fr.weefle.waze.old.BossBarOld;
import fr.weefle.waze.old.ParticleOld;

public class NMS {
	
	private Waze main;
	public NMS(Waze main) {
		this.main = main;
	}
	
	private static NMS instance;
	private ActionBarAPI actionbar;
	private Title title;
	private BossBarAPI bossbar;
	private Ping ping;
	private Tablist tablist;
	private ParticleAPI particle;
	private Nametag nametag;
	private ScoreBoard scoreboard;
	private AutoRespawnAPI autorespawn;
	
	public boolean isSet() {
		String version;

	try {

		version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];

	} catch (ArrayIndexOutOfBoundsException exception) {
		return false;
	}

	Bukkit.getLogger().info("Your server is running version " + version);
	
	scoreboard = new ScoreBoard();
	ping = new Ping();
	nametag = new Nametag();
	tablist = new Tablist();
	
	if (version.equals("v1_8_R3") || version.equals("v1_8_R2") || version.equals("v1_8_R1")) {
    	particle = new ParticleOld();
    	title = new Title();
		autorespawn = new AutoRespawnOld(main);
		bossbar = new BossBarOld();
		actionbar = new ActionBarOld();
    }else if (version.equals("v1_7_R4") || version.equals("v1_7_R3") || version.equals("v1_7_R2") || version.equals("v1_7_R1")){
    	particle = new ParticleOld();
		autorespawn = new AutoRespawnOld(main);
		bossbar = new BossBarOld();
    }else if(version.equals("v1_9_R1") || version.equals("v1_9_R2")) {
    	title = new Title();
    	particle = new ParticleNew();
    	autorespawn = new AutoRespawnNew();
		bossbar = new BossBarNew(main);
		actionbar = new ActionBarOld();
}else {
	title = new Title();
	particle = new ParticleNew();
	autorespawn = new AutoRespawnNew();
	bossbar = new BossBarNew(main);
	actionbar = new ActionBarNew();
}
	return true;
}
	
	public ActionBarAPI getActionbar() {
        return actionbar;
    }
    public Title getTitle() {
        return title;
    }
public BossBarAPI getBossBar(){
    return bossbar;
}
public Ping getPing(){
    return ping;
}
public ScoreBoard getScoreBoard(){
    return scoreboard;
}

public AutoRespawnAPI getAutoRespawn(){
    return autorespawn;
}

public Nametag getNametag(){
    return nametag;
}

public Tablist getTablist(){
    return tablist;
}

public ParticleAPI getParticles(){
    return particle;
}

public static NMS getInstance() {
	return instance;
}

public static void setInstance(NMS instance) {
	NMS.instance = instance;
}}

