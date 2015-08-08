package tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import constants.AreaConstants;
import constants.ObjectContants;

public class EnterAltar extends Task<ClientContext> {

	public EnterAltar(ClientContext clientContext) {
		super(clientContext);
		state = "Enter altar";
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().inventoryContainsEssence() && AreaConstants.ALTAR_AIR_OUTSIDE.contains(ctx.players.local());
	}

	@Override
	public void execute() {
		GameObject altar = ctx.objects.select().id(ObjectContants.ALTER_AIR_ENTRANCE).poll();
		
		altar.interact("Enter");
		
		Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() == 1;
            }
        }, 300, 10);
	}

}
