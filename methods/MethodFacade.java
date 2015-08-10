package methods;

import org.powerbot.script.rt4.ClientContext;

public class MethodFacade {
	
	private InventoryMethods inventoryMethods;
	private BankMethods bankMethods;
	private NPCMethods npcMethods;
	private ObjectMethods objectMethods;
	
	public ObjectMethods objectMethods() {
		return objectMethods;
	}
	
	public InventoryMethods inventoryMethods() {
		return this.inventoryMethods;
	}
	
	public BankMethods bankMethods(){
		return this.bankMethods;
	}
	
	public NPCMethods npcMethods() {
		return npcMethods;
	}

	public MethodFacade(ClientContext clientContext){
		this.inventoryMethods = new InventoryMethods(clientContext);
		this.bankMethods = new BankMethods(clientContext);
		this.npcMethods = new NPCMethods(clientContext);
		this.objectMethods = new ObjectMethods(clientContext);
	}




}
