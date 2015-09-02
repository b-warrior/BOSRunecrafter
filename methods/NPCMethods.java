package methods;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

public class NPCMethods extends Methods{

	public NPCMethods(ClientContext clientContext) {
		super(clientContext);
	}
	
	
	public void tradeWithNPC(int id){
		final Npc npc = clientContext.npcs.select().id(id).nearest().poll();
		npc.interact("Trade");
		
	    Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return npc.inViewport() && !clientContext.players.local().inMotion();
            }
        }, 200, 10);
	}
	
	
	public void walkToNPC(int id){
		Npc npc = clientContext.npcs.select().id(id).nearest().poll();
		clientContext.movement.newTilePath(npc.tile()).traverse();
	}
	
	public boolean npcIsOnScreen(int id){
		Npc npc = clientContext.npcs.select().id(id).nearest().poll();
		
		return npc.inViewport();
	}
	
	public boolean npcIsOnScreen(int[] id){
		Npc npc = clientContext.npcs.select().id(id).nearest().poll();
		
		return npc.inViewport();
	}
	
	public boolean npcIsClose(int id){
		return !clientContext.npcs.select().id(id).nearest().isEmpty();
	}
	
	public boolean npcIsClose(int[] id){
		return !clientContext.npcs.select().id(id).nearest().isEmpty();
	}

}
