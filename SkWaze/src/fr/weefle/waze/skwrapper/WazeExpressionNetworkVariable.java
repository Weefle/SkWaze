package fr.weefle.waze.skwrapper;

import ch.njol.util.coll.CollectionUtils;
import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;
import fr.weefle.waze.data.PluginMessageRequest;
import ch.njol.skript.classes.Changer;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;

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
        
        /*PluginMessageRequest pmr = new PluginMessageRequest("SkWrapper-network-variable") {


			@Override
			public void onAnswer(PluginMessage response) {
				
				Object var = response.getDataAsInt("value");
		            return new Object[] { var };

			}

		};
		pmr.setData("ID", ID);
		Waze.getComApi().sendRequest(pmr);*/
        
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
        	PluginMessage pm = new PluginMessage("SkWrapper-network-variable-remove");
            pm.setData("ID", ID);
            Waze.getComApi().sendMessage(pm); 
        }
    }
    
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.REMOVE) {
            return (Class<?>[])CollectionUtils.array((Object[])new Class[] { Object.class });
        }
        return null;
    }
}