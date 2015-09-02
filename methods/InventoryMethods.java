package methods;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game.Tab;

public class InventoryMethods extends Methods{

	public InventoryMethods(ClientContext clientContext) {
		super(clientContext);
	}

	
	public boolean iventoryContainsItem(int id){
		try{
		if(clientContext.game.tab() != Tab.INVENTORY){
			clientContext.game.tab(Tab.INVENTORY);
		}
		return clientContext.inventory.select().id(id).count() > 0 ;
		}catch(Exception ex){
			Condition.sleep(400);
			return iventoryContainsItem(id);
		}
	}
}