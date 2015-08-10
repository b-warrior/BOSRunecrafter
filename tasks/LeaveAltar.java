package tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class LeaveAltar extends Task<ClientContext>{

	private int essence;
	private int portal;
	
	public LeaveAltar(ClientContext clientContext, int essence, int portal) {
		super(clientContext);
		this.state = "Leaving altar";
		this.essence = essence;
		this.portal = portal;
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& methods.objectMethods().objectIsClose(portal);
	}

	@Override
	public void execute() {
		
		if (!methods.objectMethods().objectIsOnScreen(this.portal)){
			methods.objectMethods().walkToObject(this.portal);
			return;
		}
		
		GameObject altar = ctx.objects.select().id(this.portal).poll();
		
		altar.interact("USE");
		
		Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() == 1;
            }
        }, 100, 10);
	}
	
}
