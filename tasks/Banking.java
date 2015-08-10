package tasks;

import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;


public class Banking extends Task<ClientContext> {
	
	private int essence;
	private int[] bankers;
	
	public Banking(ClientContext clientContext,int essence, int[] bankers) {
		super(clientContext);
		this.state = "Banking";
		this.essence = essence;
		this.bankers = bankers;
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& methods.npcMethods().npcIsClose(bankers);
	}

	@Override
	public void execute() {
		
		methods.bankMethods().openBank();
        
        if(ctx.inventory.items().length > 0){
        	ctx.bank.depositInventory();
        }
        
        ctx.bank.withdraw(this.essence, Bank.Amount.ALL);
	}

}
