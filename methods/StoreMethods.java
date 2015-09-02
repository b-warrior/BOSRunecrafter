package methods;

import java.awt.Point;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Menu.Command;
import org.powerbot.script.rt4.Widget;

public class StoreMethods extends Methods{

	public StoreMethods(ClientContext clientContext) {
		super(clientContext);
	}
	
	public boolean isShopOpen(){
		Widget shopWidget = clientContext.widgets.select().id(300).poll();
		Component closeButton = shopWidget.component(91);
		
		return closeButton.visible();
	}
	
	private boolean arrayContains(int[] ids,int id){
		
		for(int i:ids){
			if(i == id){
				return true;
			}
		}
		return false;
		
	}
	
	public boolean storeIsFull(){
		Widget shopWidget = clientContext.widgets.select().id(300).poll();
		Component shopComponent = shopWidget.component(75);
		return !arrayContains(shopComponent.itemIds(),-1);
	}
	
	public boolean shopContainsItem(int id){
		Widget shopWidget = clientContext.widgets.select().id(300).poll();
		Component shopComponent = shopWidget.component(75);
		
		return arrayContains(shopComponent.itemIds(),id);
	}
	
	public void buyItem(int id){
		Widget shopWidget = clientContext.widgets.select().id(300).poll();
		Component shopComponent = shopWidget.component(75);
		
		Point startPoint = shopComponent.screenPoint();
		startPoint.x += 18;
		startPoint.y += 18;
		
		
		Point item = new Point(); 
	
		
		for(int i = 0; i < 8;i++){
			for(int j = 0; j < 5;j++){
			  if(shopComponent.itemIds()[i + (j*8)] == id){
				  int randomX = Random.nextInt(-10, 10);
					int randomY = Random.nextInt(-10, 10);
				  item.x = startPoint.x + (46 * i) + randomX;
				  item.y = startPoint.y + (46 * j) + randomY;
			  }
			}	
		}
		
		clientContext.input.move(item);
		clientContext.input.click(false);
		
		Condition.sleep(Random.nextInt(200, 400));
		
		clientContext.menu.click(new Filter<Command>(){

			@Override
			public boolean accept(Command command) {
				
				
				return command.action.equals("Buy 10");
			}
		});
		
		Condition.sleep(Random.nextInt(200, 400));
		
	}

}
