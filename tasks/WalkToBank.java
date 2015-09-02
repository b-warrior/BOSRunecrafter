package tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;


public class WalkToBank extends Task<ClientContext> {
	
	private int essence;
	private Tile[] path; 
	private int bankBooth;
	
	public WalkToBank(ClientContext clientContext,int essence,Tile[] path,int bankBooth) {
		super(clientContext);
		state = "Walking to bank";
		this.essence = essence;
		this.path = path;
		this.bankBooth = bankBooth;
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& !methods.objectMethods().objectIsOnScreen(bankBooth)
				&& ctx.players.local().animation() == -1
		        && !methods.storeMethods().isShopOpen();
	}

	@Override
	public void execute() {
		ctx.movement.newTilePath(this.path).reverse().traverse();
		Condition.sleep(Random.nextInt(500, 700));
	}

}
