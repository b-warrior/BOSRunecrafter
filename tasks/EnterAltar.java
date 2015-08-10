package tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class EnterAltar extends Task<ClientContext> {

	private int essence;
	private int alterEnterance;
	
	public EnterAltar(ClientContext clientContext, int essence, int alterEnterance) {
		super(clientContext);
		this.state = "Enter altar";
		this.essence = essence;
		this.alterEnterance = alterEnterance;
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& methods.objectMethods().objectIsOnScreen(alterEnterance);
	}

	@Override
	public void execute() {
		GameObject altar = ctx.objects.select().id(this.alterEnterance).poll();
		
		altar.interact("Enter");
		
		Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() == 1;
            }
        }, 100, 10);
	}

}
