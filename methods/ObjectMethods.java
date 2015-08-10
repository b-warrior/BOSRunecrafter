package methods;

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
		clientContext.movement.newTilePath(gameObject.tile()).traverse();
	}
	
	public boolean objectIsClose(int id){
		return !clientContext.objects.select().id(id).nearest().isEmpty();
	}
	
	public boolean objectIsClose(int[] id){
		return !clientContext.objects.select().id(id).nearest().isEmpty();
	}

}
