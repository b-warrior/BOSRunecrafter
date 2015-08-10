package methods;

import org.powerbot.script.rt4.ClientContext;

public class InventoryMethods extends Methods{

	public InventoryMethods(ClientContext clientContext) {
		super(clientContext);
	}

	
	public boolean iventoryContainsItem(int id){
		return clientContext.inventory.select().id(id).count() > 0 ;
	}
}