package tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class CraftRunes extends Task<ClientContext>{

	private int essence;
	private int altar;
	
	public CraftRunes(ClientContext clientContext, int essence, int altar) {
		super(clientContext);
		this.state = "Crafting runes";
		this.essence = essence;
		this.altar = altar;
	}

	@Override
	public boolean activate() {
		return methods.inventoryMethods().iventoryContainsItem(this.essence) 
				&& methods.objectMethods().objectIsClose(altar);
	}

	@Override
	public void execute() {
		
		if (!methods.objectMethods().objectIsOnScreen(this.altar)){
			methods.objectMethods().walkToObject(this.altar);
			return;
		}
		
		GameObject altar = ctx.objects.select().id(this.altar).poll();

		altar.interact("Craft-rune");
		
		Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() == 1;
            }
        }, 200, 10);
	}

}
