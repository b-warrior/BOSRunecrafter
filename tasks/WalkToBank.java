package tasks;

import org.powerbot.script.rt4.ClientContext;

import constants.AreaConstants;
import constants.PathConstants;

public class WalkToBank extends Task<ClientContext> {
	
	public WalkToBank(ClientContext clientContext) {
		super(clientContext);
		state = "Walking to bank";
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().inventoryContainsEssence() 
				&& !AreaConstants.BANKING_FALADOR.contains(ctx.players.local()) && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		ctx.movement.newTilePath(PathConstants.AIRRUNEPATH).reverse().traverse();
	}

}
