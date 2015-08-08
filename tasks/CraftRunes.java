package tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import constants.AreaConstants;
import constants.ObjectContants;

public class CraftRunes extends Task<ClientContext>{


	public CraftRunes(ClientContext clientContext) {
		super(clientContext);
		state = "Crafting runes";
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().inventoryContainsEssence() && AreaConstants.ALTAR_AIR_INSIDE.contains(ctx.players.local());
	}

	@Override
	public void execute() {
		GameObject altar = ctx.objects.select().id(ObjectContants.ALTER_AIR).poll();

		altar.interact("Craft-rune");
		
		Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() == 1;
            }
        }, 300, 10);
	}

}
