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
import me.dommi2212.BungeeBridge.packets.PacketCustom;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeExpressionNetworkVariable extends SimpleExpression<Object>
{
    private Variable variable;
    private VariableString variableString;
    private Object var;
    
    public Class<?> getReturnType() {
        return Object.class;
    }
    
    public boolean isSingle() {
        return !this.variable.isList();
    }
    
    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!(e[0] instanceof Variable)) {
            Skript.error("Network Variables must be a variable!");
            return false;
        }
        if (!((Variable)e[0]).isList()) {
            this.variable = (Variable)e[0];
            final String var = this.variable.toString().substring(1, this.variable.toString().length() - 1);
            //Bukkit.getLogger().warning(var);
            this.variableString = VariableString.newInstance(var, StringMode.VARIABLE_NAME);
            return true;
        }
        Skript.error("Network Variables can't be lists at the moment!");
        return false;
    }
    
    public String toString(@Nullable final Event e, final boolean arg1) {
        return "[skwrapper] (global|network) variable [(from|of)] %object%";
    }
    
    @Nullable
    protected Object[] get(final Event e) {
        final String ID = this.variableString.toString(e);
        
        return new Object[] {Variables.getVariable(ID, (Event)null, false)};
    }
    
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        final String ID = this.variableString.toString(e);
        if (mode == Changer.ChangeMode.SET) {
            StringBuilder sb = new StringBuilder();
            sb.append(ID);
            sb.append(" ");
            sb.append(delta[0]);
            String str = sb.toString();
            PacketCustom packet = new PacketCustom("SkWrapper-network-variable-set", str);
            Variables.setVariable(ID, packet.send(), null, false);
        }
        else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            StringBuilder sb = new StringBuilder();
            sb.append(ID);
            String str = sb.toString();
            PacketCustom packet = new PacketCustom("SkWrapper-network-variable-reset", str);
            Variables.setVariable(ID, packet.send(), null, false);
        }
    }
    
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            return (Class<?>[])CollectionUtils.array((Object[])new Class[] { Object.class });
        }
        return null;
    }
}