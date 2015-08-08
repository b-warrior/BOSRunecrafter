package tasks;


import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import constants.AreaConstants;
import constants.PathConstants;

public class WalkToAltar extends Task<ClientContext>{

	public WalkToAltar(ClientContext clientContext) {
		super(clientContext);
		state = "Walking to altar";
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().inventoryContainsEssence() && !AreaConstants.ALTAR_AIR_OUTSIDE.contains(ctx.players.local())
				&& ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		ctx.movement.newTilePath(PathConstants.AIRRUNEPATH).traverse();
		Condition.sleep(600);
	}

}
