package tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

import constants.ObjectContants;
import constants.PathConstants;

public class TeleportToArena extends Task<ClientContext>{

	private int essence;
	
	public TeleportToArena(ClientContext clientContext, int essence) {
		super(clientContext);
		this.essence = essence;
		this.state = "Going to altar";
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().iventoryContainsItem(this.essence)  
				&& !methods.objectMethods().objectIsClose(ObjectContants.ALTER_FIRE_ENTRANCE)
				&& ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		
		if(ctx.bank.opened()){
			ctx.bank.close();
			return;
		}
		
		if(methods.objectMethods().objectIsOnScreen(ObjectContants.BANK_CHEST_FIRE)){
			methods.teleportMethods().teleportToArena();
			return;
		}
		
		ctx.movement.newTilePath(PathConstants.FIRERINGPATH).traverse();
		Condition.sleep(Random.nextInt(500, 700));
	}

}
