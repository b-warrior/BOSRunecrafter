package methods;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import constants.NPCConstants;

public class BankMethods extends Methods{

	public BankMethods(ClientContext clientContext) {
		super(clientContext);
	}

	public void openBank(){
		
		Npc banker = clientContext.npcs.select().id(NPCConstants.BANKERIDS).nearest().poll();
		
	    Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return banker.inViewport() && !clientContext.players.local().inMotion();
            }
        }, 200, 10);
	    
	    banker.interact("Bank");
	    
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return clientContext.bank.opened();
            }
        }, 200, 10);
	}
	
}
