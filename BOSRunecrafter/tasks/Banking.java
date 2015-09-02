package tasks;

import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;


public class Banking extends Task<ClientContext> {
	
	private int essence;
	private int bankBooth;
	private String action;
	

	
	public Banking(ClientContext clientContext,int essence, int bankBooth,String action) {
		super(clientContext);
		this.state = "Banking";
		this.essence = essence;
		this.bankBooth = bankBooth;
		this.action = action;
	}
	
	public Banking(ClientContext clientContext,int essence, int bankBooth) {
		this(clientContext,essence,bankBooth,"Bank");
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
		
		methods.bankMethods().openBank(this.bankBooth,this.action);
        
        if(ctx.inventory.items().length > 0){
        	ctx.bank.depositInventory();
        }
        
        ctx.bank.withdraw(this.essence, Bank.Amount.ALL);
	}

}
