package fr.weefle.waze.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class WazeEffectRecipe
  extends Effect
{
  private boolean isShapeless;
  private Expression<ItemType> out;
  private Expression<ItemType> m1;
  private Expression<ItemType> m2;
  private Expression<ItemType> m3;
  private Expression<ItemType> m4;
  private Expression<ItemType> m5;
  private Expression<ItemType> m6;
  private Expression<ItemType> m7;
  private Expression<ItemType> m8;
  private Expression<ItemType> m9;
  
  protected void execute(Event event)
  {
    ItemType io = (ItemType)out.getSingle(event);
    ItemType i1 = (ItemType)m1.getSingle(event);
    ItemType i2 = (ItemType)m2.getSingle(event);
    ItemType i3 = (ItemType)m3.getSingle(event);
    ItemType i4 = (ItemType)m4.getSingle(event);
    ItemType i5 = (ItemType)m5.getSingle(event);
    ItemType i6 = (ItemType)m6.getSingle(event);
    ItemType i7 = (ItemType)m7.getSingle(event);
    ItemType i8 = (ItemType)m8.getSingle(event);
    ItemType i9 = (ItemType)m9.getSingle(event);
    if ((io == null) || (i1 == null) || (i2 == null) || (i3 == null) || (i4 == null) || (i5 == null) || (i6 == null) || (i7 == null) || (i8 == null) || (i9 == null)) {
      return;
    }
    if (this.isShapeless)
    {
      ShapelessRecipe r = new ShapelessRecipe(io.getRandom());
      if (i1.getRandom().getType() != Material.AIR) {
        r.addIngredient(i1.getRandom().getData());
      }
      if (i2.getRandom().getType() != Material.AIR) {
        r.addIngredient(i2.getRandom().getData());
      }
      if (i3.getRandom().getType() != Material.AIR) {
        r.addIngredient(i3.getRandom().getData());
      }
      if (i4.getRandom().getType() != Material.AIR) {
        r.addIngredient(i4.getRandom().getData());
      }
      if (i5.getRandom().getType() != Material.AIR) {
        r.addIngredient(i5.getRandom().getData());
      }
      if (i6.getRandom().getType() != Material.AIR) {
        r.addIngredient(i6.getRandom().getData());
      }
      if (i7.getRandom().getType() != Material.AIR) {
        r.addIngredient(i7.getRandom().getData());
      }
      if (i8.getRandom().getType() != Material.AIR) {
        r.addIngredient(i8.getRandom().getData());
      }
      if (i9.getRandom().getType() != Material.AIR) {
        r.addIngredient(i9.getRandom().getData());
      }
      Skript.getInstance().getServer().addRecipe(r);
    }
    else
    {
      ShapedRecipe r = new ShapedRecipe(io.getRandom());
      r.shape(new String[] { "abc", 
        "def", 
        "ghi" });
      if (i1.getRandom().getType() != Material.AIR) {
        r.setIngredient('a', i1.getRandom().getData());
      }
      if (i2.getRandom().getType() != Material.AIR) {
        r.setIngredient('b', i2.getRandom().getData());
      }
      if (i3.getRandom().getType() != Material.AIR) {
        r.setIngredient('c', i3.getRandom().getData());
      }
      if (i4.getRandom().getType() != Material.AIR) {
        r.setIngredient('d', i4.getRandom().getData());
      }
      if (i5.getRandom().getType() != Material.AIR) {
        r.setIngredient('e', i5.getRandom().getData());
      }
      if (i6.getRandom().getType() != Material.AIR) {
        r.setIngredient('f', i6.getRandom().getData());
      }
      if (i7.getRandom().getType() != Material.AIR) {
        r.setIngredient('g', i7.getRandom().getData());
      }
      if (i8.getRandom().getType() != Material.AIR) {
        r.setIngredient('h', i8.getRandom().getData());
      }
      if (i9.getRandom().getType() != Material.AIR) {
        r.setIngredient('i', i9.getRandom().getData());
      }
      Skript.getInstance().getServer().addRecipe(r);
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "create recipe";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3)
  {
    out = (Expression<ItemType>) arg0[0];
    m1 = (Expression<ItemType>) arg0[1];
    m2 = (Expression<ItemType>) arg0[2];
    m3 = (Expression<ItemType>) arg0[3];
    m4 = (Expression<ItemType>) arg0[4];
    m5 = (Expression<ItemType>) arg0[5];
    m6 = (Expression<ItemType>) arg0[6];
    m7 = (Expression<ItemType>) arg0[7];
    m8 = (Expression<ItemType>) arg0[8];
    m9 = (Expression<ItemType>) arg0[9];
    isShapeless = (arg1 == 1);
    return true;
  }
}