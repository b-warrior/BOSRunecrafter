package tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;


public class WalkToBank extends Task<ClientContext> {
	
	private int essence;
	private Tile[] path; 
	private int[] bankers;
	
	public WalkToBank(ClientContext clientContext,int essence,Tile[] path,int[] bankers) {
		super(clientContext);
		state = "Walking to bank";
		this.essence = essence;
		this.path = path;
		this.bankers = bankers;
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& !methods.npcMethods().npcIsClose(bankers)
				&& ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		ctx.movement.newTilePath(this.path).reverse().traverse();
	}

}
