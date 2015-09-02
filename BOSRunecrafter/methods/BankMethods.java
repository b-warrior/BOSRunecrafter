package methods;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;


public class BankMethods extends Methods{

	public BankMethods(ClientContext clientContext) {
		super(clientContext);
	}

	public void openBank(int bankBoothID, String action){
		
		final GameObject bankBooth = clientContext.objects.select().id(bankBoothID).nearest().poll();
		
	    Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bankBooth.inViewport() && !clientContext.players.local().inMotion();
            }
        }, 200, 10);
	    
	    bankBooth.interact(action);
	    
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return clientContext.bank.opened();
            }
        }, 200, 10);
	}
	
}
