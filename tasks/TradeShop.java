package tasks;


import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Menu.Command;

public class TradeShop extends Task<ClientContext>{

	private int essence;
	private int notedEssence;
	private int shopper;
	
	public TradeShop(ClientContext clientContext, int essence, int notedEssence, int shopper) {
		super(clientContext);
		this.state = "Shopping";
		this.essence = essence;
		this.shopper = shopper;
		this.notedEssence = notedEssence;
	}

	@Override
	public boolean activate() {
		return (!methods.inventoryMethods().iventoryContainsItem(this.essence)  ||  (ctx.inventory.select().count() < 28 && methods.inventoryMethods().iventoryContainsItem(this.notedEssence)))
				&& methods.npcMethods().npcIsClose(this.shopper);
	}

	@Override
	public void execute() {
		
		
		if(!methods.storeMethods().isShopOpen()){
			if (!methods.npcMethods().npcIsOnScreen(this.shopper)){
				methods.npcMethods().walkToNPC(this.shopper);
				return;
			}
		
		
			methods.npcMethods().tradeWithNPC(this.shopper);
			
			Condition.sleep(Random.nextInt(400, 800));
		}else{
			if(methods.storeMethods().storeIsFull() && !methods.storeMethods().shopContainsItem(essence) ){
				return;
			}
			
			if(methods.storeMethods().shopContainsItem(essence)){
				System.out.println("Buying more essence");
				methods.storeMethods().buyItem(essence);
			}else{
				
				
				
				ctx.inventory.select().id(notedEssence).poll().interact(false, new Filter<Command>(){

					@Override
					public boolean accept(Command command) {
						String option;
						int freeRoom = 28 - ctx.inventory.select().count();
						
						if (freeRoom > 10)
							option = "Sell 10";
						else if (freeRoom < 10 && freeRoom >= 5)
							option = "Sell 5";
						else
							option = "Sell 1";
						
						
						System.out.println( " Action= " + command.action + " " + "Option= " + command.option + " " +(command.action.equals(option)));
						return command.action.equals(option);
					}
				});
				Condition.sleep(Random.nextInt(400, 600));
			}
			
		}
		
		
		
	}

}
