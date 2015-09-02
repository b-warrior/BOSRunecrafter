package methods;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class ObjectMethods extends Methods{

	public ObjectMethods(ClientContext clientContext) {
		super(clientContext);
	}
	
	public boolean objectIsOnScreen(int id){
		GameObject gameObject = clientContext.objects.select().id(id).nearest().poll();
		
		return gameObject.inViewport();
		
	}

	public void walkToObject(int id) {
		GameObject gameObject = clientContext.objects.select().id(id).nearest().poll();
		clientContext.movement.newTilePath(clientContext.movement.closestOnMap(gameObject)).traverse();
		Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !clientContext.players.local().inMotion();
            }
        }, 200, 20);
	}
	
	public boolean objectIsClose(int id){
		return !clientContext.objects.select().id(id).nearest().isEmpty();
	}
	
	public boolean objectIsClose(int[] id){
		return !clientContext.objects.select().id(id).nearest().isEmpty();
	}

}
