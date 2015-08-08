package methods;

import org.powerbot.script.rt4.ClientContext;

public class MethodFacade {
	
	private InventoryMethods inventoryMethods;
	private BankMethods bankMethods;
	
	public MethodFacade(ClientContext clientContext){
		this.inventoryMethods = new InventoryMethods(clientContext);
		this.bankMethods = new BankMethods(clientContext);
	}

	public InventoryMethods inventoryMethods() {
		return this.inventoryMethods;
	}
	
	public BankMethods bankMethods(){
		return this.bankMethods;
	}


}
