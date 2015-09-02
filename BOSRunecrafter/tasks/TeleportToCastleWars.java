package tasks;

import org.powerbot.script.rt4.ClientContext;

import constants.ObjectContants;

public class TeleportToCastleWars extends Task<ClientContext>{

	private int essence;
	
	public TeleportToCastleWars(ClientContext clientContext, int essence) {
		super(clientContext);
		this.essence = essence;
		this.state = "Going to castle wars";
	}

	@Override
	public boolean activate() {
		return !methods.objectMethods().objectIsClose(ObjectContants.BANK_CHEST_FIRE)
				&& !methods.inventoryMethods().iventoryContainsItem(this.essence);
	}

	@Override
	public void execute() {
		methods.teleportMethods().teleportToCastleWars();
	}

}
