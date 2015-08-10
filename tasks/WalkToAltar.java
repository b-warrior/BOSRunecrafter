package tasks;


import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class WalkToAltar extends Task<ClientContext>{

	
	private int essence;
	private Tile[] path; 
	private int altar;
	public WalkToAltar(ClientContext clientContext,int essence,Tile[] path, int altar) {
		super(clientContext);
		state = "Walking to altar";
		this.essence = essence;
		this.path = path;
		this.altar = altar;
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().iventoryContainsItem(this.essence)  
				&& !methods.objectMethods().objectIsClose(altar)
				&& ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		ctx.movement.newTilePath(this.path).traverse();
		Condition.sleep(600);
	}

}
