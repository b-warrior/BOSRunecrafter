package tasks;


import org.powerbot.script.rt4.ClientContext;

public class TradeShop extends Task<ClientContext>{

	private int essence;
	private int shopper;
	
	public TradeShop(ClientContext clientContext, int essence, int shopper) {
		super(clientContext);
		this.state = "Shopping";
		this.essence = essence;
		this.shopper = shopper;
	}

	@Override
	public boolean activate() {
		return !methods.inventoryMethods().iventoryContainsItem(this.essence)  && methods.npcMethods().npcIsClose(this.shopper);
	}

	@Override
	public void execute() {
		if (!methods.npcMethods().npcIsOnScreen(this.shopper)){
			methods.npcMethods().walkToNPC(this.shopper);
			return;
		}
		
		methods.npcMethods().tradeWithNPC(this.shopper);
		

	}

}
