package tasks;

import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;


public class Banking extends Task<ClientContext> {
	
	private int essence;
	private int bankBooth;
	
	public Banking(ClientContext clientContext,int essence, int bankBooth) {
		super(clientContext);
		this.state = "Banking";
		this.essence = essence;
		this.bankBooth = bankBooth;
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& methods.objectMethods().objectIsClose(this.bankBooth);
	}

	@Override
	public void execute() {
		
		if (!methods.objectMethods().objectIsOnScreen(this.bankBooth)){
			methods.objectMethods().walkToObject(this.bankBooth);
			return;
		}
		
		methods.bankMethods().openBank();
        
        if(ctx.inventory.items().length > 0){
        	ctx.bank.depositInventory();
        }
        
        ctx.bank.withdraw(this.essence, Bank.Amount.ALL);
	}

}
