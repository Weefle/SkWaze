package fr.weefle.waze;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.event.Event;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectClearRecipe
  extends Effect
{
  private Expression<ItemType> items;
  
  protected void execute(Event event)
  {
    ItemType[] arrayOfItemType;
    int j = (arrayOfItemType = (ItemType[])this.items.getAll(event)).length;
    for (int i = 0; i < j; i++)
    {
      ItemType type = arrayOfItemType[i];
      try
      {
        Object craftingInstance = Reflection.nmsClass("CraftingManager").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        Object recipes = Reflection.getField(craftingInstance.getClass(), "recipes").get(craftingInstance);
        Reflection.getMethod(recipes.getClass(), "remove", new Class[0]).invoke(recipes, new Object[] { Integer.valueOf(type.getRandom().getType().getMaxStackSize()) });
      }
      catch (IllegalAccessException|NoSuchMethodException|InvocationTargetException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public String toString(Event event, boolean b)
  {
    return "remove recipe";
  }
  
  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult)
  {
    items = (Expression<ItemType>) expressions[0];
    return true;
  }
}