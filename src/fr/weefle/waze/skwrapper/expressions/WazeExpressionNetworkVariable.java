package fr.weefle.waze.skwrapper.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.dommi2212.BungeeBridge.packets.PacketVariable;
import me.dommi2212.BungeeBridge.packets.PacketVariableGet;
import me.dommi2212.BungeeBridge.packets.PacketVariableRemove;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeExpressionNetworkVariable extends SimpleExpression<Object>
{
    private static Variable<?> variable;
    private VariableString variableString;
    
    public Class<?> getReturnType() {
        return Object.class;
    }
    
    public boolean isSingle() {
        return !this.variable.isList();
    }

    @SuppressWarnings("unchecked")
    private <T> Expression<T> getExpression(Expression<?> expr) {
        if (expr instanceof UnparsedLiteral) {
            Literal<?> parsedLiteral = ((UnparsedLiteral) expr).getConvertedExpression(Object.class);
            return (Expression<T>) (parsedLiteral == null ? expr : parsedLiteral);
        }
        return (Expression<T>) expr;
    }
    
    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (e[0] instanceof Variable) {
            variable = (Variable<?>) e[0];
        } else {
            Expression<?> expression = getExpression(e[0]);
            if (expression instanceof Variable) {
                variable = (Variable<?>) expression;
            }
        }
        if (variable != null) {
            if (variable.isLocal()) {
                Skript.error("Network Variables can not be a local variable.");
                return false;
            }
            //substring the variable ends { and } from the variable.
            String var = variable.toString().substring(1, variable.toString().length() - 1);
            //creates a new VariableString which is what Skript accepts to get Variables.
            variableString = VariableString.newInstance(var, StringMode.VARIABLE_NAME);
            return true;
        }
        Skript.error("Network Variables must be in a variable format!");
        return false;
    }

    public String toString(@Nullable final Event e, final boolean arg1) {
        return "[skwrapper] (global|network) variable [(from|of)] %objects%";
    }
    
    @Nullable
    protected Object[] get(final Event e) {
        final String ID = this.variableString.toString(e);
        
        return new Object[] {new PacketVariableGet(ID).send()};
    }
    
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        final String ID = this.variableString.toString(e);
        if (mode == Changer.ChangeMode.SET) {
            new PacketVariable(ID, delta[0]).send();
        }
        else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            new PacketVariableRemove(ID).send();
        }
    }
    
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
            return (Class<?>[])CollectionUtils.array((Object[])new Class[] { Object.class });
        }
        return null;
    }
}