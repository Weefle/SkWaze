package fr.weefle.waze.skwrapper;

import ch.njol.util.coll.CollectionUtils;
import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.UnparsedLiteral;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleExpression;

public class WazeExpressionNetworkVariable extends SimpleExpression<Object>
{
    private Variable<?> variable;
    private VariableString variableString;
    
    public Class<?> getReturnType() {
        return Object.class;
    }
    
    public boolean isSingle() {

        return !this.variable.isList();

    }
    
    private <T> Expression<T> getExpression(final Expression<?> expr) {
        if (expr instanceof UnparsedLiteral) {
            final Literal<?> parsedLiteral = (Literal<?>)((UnparsedLiteral)expr).getConvertedExpression(new Class[] { Object.class });
            return (Expression<T>)((parsedLiteral == null) ? expr : parsedLiteral);
        }
        return (Expression<T>)expr;
    }
    
    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
    	if (e[0] instanceof Variable) {
            this.variable = (Variable<?>)e[0];
        }
        else {
            final Expression<?> expression = this.getExpression(e[0]);
            if (expression instanceof Variable) {
                this.variable = (Variable<?>)expression;
            }
        }
        if (this.variable == null) {
            Skript.error("Network Variables must be in a variable format!");
            return false;
        }
        if (this.variable.isLocal()) {
            Skript.error("Network Variables can not be a local variable.");
            return false;
        }
        final String var = this.variable.toString().substring(1, this.variable.toString().length() - 1);
        this.variableString = VariableString.newInstance(var, StringMode.VARIABLE_NAME);
        return true;
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
            PluginMessage pm = new PluginMessage("SkWrapper-network-variable-set");
            pm.setData("ID", ID);
            pm.setData("value", delta[0].toString());
            Waze.getComApi().sendMessage(pm);
        }
        else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
        	PluginMessage pm = new PluginMessage("SkWrapper-network-variable-reset");
            pm.setData("ID", ID);
            Waze.getComApi().sendMessage(pm); 
        }
        else if (mode == Changer.ChangeMode.ADD) {
        	PluginMessage pm = new PluginMessage("SkWrapper-network-variable-add");
            pm.setData("ID", ID);
            pm.setData("value", delta[0].toString());
            Waze.getComApi().sendMessage(pm); 
        }
        else if (mode == Changer.ChangeMode.REMOVE) {
        	PluginMessage pm = new PluginMessage("SkWrapper-network-variable-remove");
            pm.setData("ID", ID);
            pm.setData("value", delta[0].toString());
            Waze.getComApi().sendMessage(pm); 
        }
        else if (mode == Changer.ChangeMode.REMOVE_ALL) {
        	PluginMessage pm = new PluginMessage("SkWrapper-network-variable-remove-all");
            pm.setData("ID", ID);
            Waze.getComApi().sendMessage(pm); 
        }
    }
    
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.DELETE) {
            return (Class<?>[])CollectionUtils.array((Object[])new Class[] { (Class)(this.isSingle() ? Object.class : Object[].class) });
        }
        return null;
    }

}