package methods;

import org.powerbot.script.rt4.ClientContext;

import constants.ItemConstants;

public class InventoryMethods extends Methods{

	public InventoryMethods(ClientContext clientContext) {
		super(clientContext);
	}

	public boolean inventoryContainsEssence(){
		//System.out.println("count " + clientContext.inventory.select().id(ItemConstants.ESSENCE_NORMAL).count());
		return clientContext.inventory.select().id(ItemConstants.ESSENCE_NORMAL).count() > 0 ;
	}
}