package fr.weefle.waze.skwrapper.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.dommi2212.BungeeBridge.packets.PacketVariable;
import me.dommi2212.BungeeBridge.packets.PacketVariableRemove;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeExpressionNetworkVariable extends SimpleExpression<Object>
{
    private Variable variable;
    private VariableString variableString;
    
    public Class<?> getReturnType() {
        return Object.class;
    }
    
    public boolean isSingle() {
        return !this.variable.isList();
    }
    
    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        /*if (!(e[0] instanceof Variable)) {
            Skript.error("Network Variables must be a variable!");
            return false;
        }
        if (!((Variable)e[0]).isList()) {*/
        if((Variable)e[0]!=null){
            this.variable = (Variable)e[0];
            final String var = this.variable.toString().substring(1, this.variable.toString().length() - 1);
            //Bukkit.getLogger().warning(var);
            this.variableString = VariableString.newInstance(var, StringMode.VARIABLE_NAME);
            return true;
        }
        //Skript.error("Network Variables can't be lists at the moment!");
        Skript.error("Network Variables can't be null!");
        return false;
    }
    
    public String toString(@Nullable final Event e, final boolean arg1) {
        return "[skwrapper] (global|network) variable [(from|of)] %objects%";
    }
    
    @Nullable
    protected Object[] get(final Event e) {
        final String ID = this.variableString.toString(e);
        
        return new Object[] {Variables.getVariable(ID, null, true)};
    }
    
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        final String ID = this.variableString.toString(e);
        if (mode == Changer.ChangeMode.SET) {
            PacketVariable packet = new PacketVariable(ID, delta[0]);
            Variables.setVariable(ID, packet.send(), null, true);
        }
        else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            new PacketVariableRemove(ID).send();
            Variables.setVariable(ID, null, null, true);
        }
    }
    
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            return (Class<?>[])CollectionUtils.array((Object[])new Class[] { Object.class });
        }
        return null;
    }
}