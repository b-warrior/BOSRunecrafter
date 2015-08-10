package methods;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import constants.ObjectContants;

public class BankMethods extends Methods{

	public BankMethods(ClientContext clientContext) {
		super(clientContext);
	}

	public void openBank(){
		
		GameObject bankBooth = clientContext.objects.select().id(ObjectContants.BANK_BOOTH).nearest().poll();
		
	    Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bankBooth.inViewport() && !clientContext.players.local().inMotion();
            }
        }, 200, 10);
	    
	    bankBooth.interact("Bank");
	    
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return clientContext.bank.opened();
            }
        }, 200, 10);
	}
	
}
