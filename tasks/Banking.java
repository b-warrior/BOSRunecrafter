package tasks;

import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;

import constants.AreaConstants;
import constants.ItemConstants;


public class Banking extends Task<ClientContext> {
	
	public Banking(ClientContext clientContext) {
		super(clientContext);
		state = "Banking";
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().inventoryContainsEssence() && AreaConstants.BANKING_FALADOR.contains(ctx.players.local());
	}

	@Override
	public void execute() {
		
		methods.bankMethods().openBank();
        
        if(ctx.inventory.items().length > 0){
        	ctx.bank.depositInventory();
        }
        
        ctx.bank.withdraw(ItemConstants.ESSENCE_NORMAL, Bank.Amount.ALL);
	}

}
