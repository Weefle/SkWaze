package fr.weefle.waze.nms;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.TextLine;
import fr.weefle.waze.utils.NMS;

public class HologramAPI {

	public void createHologram(String message, Location loc, String id) {
		if(!NMS.getInstance().getHologramManager().getActiveHolograms().containsKey(id)) {
			//loc.setY(loc.getY()+2);
			Hologram hologram = new Hologram(id, loc);
		    NMS.getInstance().getHologramManager().addActiveHologram(hologram);
		    HologramLine line = new TextLine(NMS.getInstance().getHologramManager().getHologram(id), message);
		    NMS.getInstance().getHologramManager().getHologram(id).addLine(line);
		
	}}
	
	public void teleportHologram(String id, Location loc) {
		if(!NMS.getInstance().getHologramManager().getActiveHolograms().containsKey(id)) {
			//loc.setY(loc.getY()+2);
		NMS.getInstance().getHologramManager().getHologram(id).teleport(loc);
}}
	
	public void addLineHologram(String id, String msg, int index) {
		if(!NMS.getInstance().getHologramManager().getActiveHolograms().containsKey(id)) {
				HologramLine line = new TextLine(NMS.getInstance().getHologramManager().getHologram(id), msg);
			    NMS.getInstance().getHologramManager().getHologram(id).addLine(line, index);
	}}
	
	public void addItemLineHologram(String id, ItemStack itemstack, int index) {
	    HologramLine line = new ItemLine(NMS.getInstance().getHologramManager().getHologram(id), itemstack);
	    NMS.getInstance().getHologramManager().getHologram(id).addLine(line, index);
	}
	
	public void removeLineHologram(String id, int index) {
		if(!NMS.getInstance().getHologramManager().getActiveHolograms().containsKey(id)) {
		NMS.getInstance().getHologramManager().getHologram(id).removeLine(NMS.getInstance().getHologramManager().getHologram(id).getLine(index));
		
	}}

	public void removeHologram(String id) {
		if(!NMS.getInstance().getHologramManager().getActiveHolograms().containsKey(id)) {
		NMS.getInstance().getHologramManager().removeActiveHologram(NMS.getInstance().getHologramManager().getHologram(id));
}}

	public String getHolograms() {
		if(NMS.getInstance().getHologramManager().getActiveHolograms()!=null) {
				return NMS.getInstance().getHologramManager().getActiveHolograms().values().toString();
		
	}
		return null;
		}

}
