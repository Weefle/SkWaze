package fr.weefle.waze.effects;

import javax.annotation.Nullable;
import fr.weefle.waze.Waze;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectBossBarRemoveAll extends Effect {

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return "remove all bossbar";
    }

    @Override
    protected void execute(Event arg0) {
        // TODO Auto-generated method stub
        Waze.getInstance().getBossBar().removeBossBarAll();
    }

}
