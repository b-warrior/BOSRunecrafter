package methods;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Game.Tab;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Player;
import org.powerbot.script.rt4.Widget;

import constants.ItemConstants;
import constants.ObjectContants;

public class TeleportMethods extends Methods{

	public TeleportMethods(ClientContext clientContext) {
		super(clientContext);
	}

	
	public void teleportToCastleWars(){
		if(clientContext.game.tab() != Tab.EQUIPMENT){
			clientContext.game.tab(Tab.EQUIPMENT);
		}
		
		Widget ringWidget = clientContext.widgets.select().id(387).poll();
		Component ringComponent = ringWidget.component(15).component(1);
		int itemId = ringComponent.itemId();
		
		if(ringComponentContainsId(ItemConstants.DUEL_RING_CASTLE)){
			interactWithRing("Castle",ItemConstants.DUEL_RING_CASTLE);
		}

		if(itemId == ItemConstants.DUEL_RING_1)
			getNewDuelRing();
		
		Condition.sleep();
		
	}
	
	public void teleportToArena(){
		if(clientContext.game.tab() != Tab.EQUIPMENT){
			clientContext.game.tab(Tab.EQUIPMENT);
		}
	
		
		
		
		if(ringComponentContainsId(ItemConstants.DUEL_RING_ARENA)){
			interactWithRing("Duel",ItemConstants.DUEL_RING_ARENA);
		}else{
			getNewDuelRing();
		}
		
		Condition.sleep();
		
	}
	
	public boolean ringComponentContainsId(int[] ids){
		Widget ringWidget = clientContext.widgets.select().id(387).poll();
		Component ringComponent = ringWidget.component(15).component(1);
		//System.out.println("Id =" + ringComponent.itemId() + " id's " + Arrays.toString(ids) + " " + Arrays.asList(ids).contains(ringComponent.itemId()) 
			//	+ " " + (ringComponent.itemId() == 2556));
		int findThis = ringComponent.itemId();
		for(int i : ids)  {
			if(i == findThis)
				return true;
		}
		
		return false;
	}
	
	private void interactWithRing(String action, int[] ids){
		Widget ringWidget = clientContext.widgets.select().id(387).poll();
		Component ringComponent = ringWidget.component(15).component(1);
		
		if (ringComponent.visible() && this.ringComponentContainsId(ids)){
			
			Tile tempTile = clientContext.players.local().tile();
			final Tile tileBefore = new Tile(tempTile.x(),tempTile.y());
			
			ringComponent.interact(action);
			
			Condition.wait(new Callable<Boolean>() {
			    

				@Override
				public Boolean call() throws Exception {
					final Player p = clientContext.players.local();
					return  p.tile().x() != tileBefore.x();
				}
			}, 500, 20);
			
		}else{
			System.out.println("no ring found");
		}
	}
	
	private void getNewDuelRing(){
		final GameObject bankBooth = clientContext.objects.select().id(ObjectContants.BANK_CHEST_FIRE).nearest().poll();
		

		
		if(!bankBooth.inViewport()){
			clientContext.movement.newTilePath(clientContext.movement.closestOnMap(bankBooth)).traverse();
		}
		
		if(clientContext.game.tab() != Tab.INVENTORY){
			clientContext.game.tab(Tab.INVENTORY);
		}
		
	    Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return bankBooth.inViewport() && !clientContext.players.local().inMotion();
            }
        }, 200, 10);
	    
	    bankBooth.interact("Use");
	    
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return clientContext.bank.opened();
            }
        }, 200, 10);
        
        if(clientContext.inventory.items().length > 0){
        	clientContext.bank.depositInventory();
        }
        
       if(clientContext.bank.opened() && clientContext.bank.select().id(ItemConstants.DUEL_RING_8).count() <= 0){
    	   clientContext.bank.close();
    	   Component logoutButton = clientContext.widgets.select().id(182).poll().component(10);
    	   if(clientContext.game.tab() != Tab.LOGOUT){
    		   clientContext.game.tab(Tab.LOGOUT);
    	   }
    	   logoutButton.click();
    	   clientContext.controller.stop();
        }
        
        clientContext.bank.withdraw(ItemConstants.DUEL_RING_8, Amount.ONE);
        
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return  clientContext.bank.close();
            }
        }, 200, 10);
       
        Item ring = clientContext.inventory.select().id(ItemConstants.DUEL_RING_ARENA).poll();
        ring.interact("Wear");
        
	}
	
}
