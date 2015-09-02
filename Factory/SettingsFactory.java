package Factory;

import java.util.Arrays;
import java.util.List;

import org.powerbot.script.rt4.ClientContext;

import constants.ItemConstants;
import constants.NPCConstants;
import constants.ObjectContants;
import constants.PathConstants;
import tasks.*;

public class SettingsFactory {
	
	private int essence;
	private ClientContext clientContext;

	
	public SettingsFactory(ClientContext clientContext){
		this.clientContext = clientContext;
	}
	
	public List<Task<ClientContext>> generateTaskList(){
		return airRunesSettings();
	}

	@SuppressWarnings("unchecked")
	public List<Task<ClientContext>> airRunesSettings(){
		return Arrays.asList(new WalkToBank(this.clientContext,essence,PathConstants.AIRRUNEPATH, ObjectContants.BANK_BOOTH_AIR)
		, new Banking(this.clientContext, essence, ObjectContants.BANK_BOOTH_AIR)
		, new WalkToAltar(this.clientContext, essence,PathConstants.AIRRUNEPATH, ObjectContants.ALTER_AIR)
		, new EnterAltar(this.clientContext, essence, ObjectContants.ALTER_AIR_ENTRANCE)
		, new CraftRunes(this.clientContext, essence, ObjectContants.ALTER_AIR)
		, new LeaveAltar(this.clientContext, essence, ObjectContants.PORTAL_AIR)
		, new CheckForRun(this.clientContext));
	}
	
	@SuppressWarnings("unchecked")
	public List<Task<ClientContext>> fireRunesSettings(){
		return Arrays.asList(new WalkToBank(this.clientContext,essence,PathConstants.FIRERUNEPATH , ObjectContants.BANK_BOOTH_FIRE)
		, new Banking(this.clientContext, essence, ObjectContants.BANK_BOOTH_FIRE)
		, new WalkToAltar(this.clientContext, essence,PathConstants.FIRERUNEPATH, ObjectContants.ALTER_FIRE)
		, new EnterAltar(this.clientContext, essence, ObjectContants.ALTER_FIRE_ENTRANCE)
		, new CraftRunes(this.clientContext, essence, ObjectContants.ALTER_FIRE)
		, new LeaveAltar(this.clientContext, essence, ObjectContants.PORTAL_FIRE)
		, new CheckForRun(this.clientContext));
	}
	
	@SuppressWarnings("unchecked")
	public List<Task<ClientContext>> fireRingRunesSettings(){
		return Arrays.asList(new TeleportToCastleWars(this.clientContext,essence)
		, new Banking(this.clientContext, essence, ObjectContants.BANK_CHEST_FIRE,"Use")
		, new TeleportToArena(this.clientContext, essence)
		, new EnterAltar(this.clientContext, essence, ObjectContants.ALTER_FIRE_ENTRANCE)
		, new CraftRunes(this.clientContext, essence, ObjectContants.ALTER_FIRE)
		, new CheckForRun(this.clientContext));
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Task<ClientContext>> natureRunesSettings(){
	 return Arrays.asList(new WalkToBank(this.clientContext,ItemConstants.ESSENCE_PURE,PathConstants.SHOPTONATURE, ObjectContants.BANK_BOOTH_FIRE)
		, new TradeShop(this.clientContext, ItemConstants.ESSENCE_PURE, ItemConstants.NOTED_ESSENCE_PURE, NPCConstants.SHOPNATURRUNE)
		, new WalkToAltarShop(this.clientContext, ItemConstants.ESSENCE_PURE,PathConstants.SHOPTONATURE, ObjectContants.ALTER_NATURE)
		, new EnterAltar(this.clientContext, ItemConstants.ESSENCE_PURE, ObjectContants.ALTER_NATURE_ENTRANCE)
		, new CraftRunes(this.clientContext, ItemConstants.ESSENCE_PURE, ObjectContants.ALTER_NATURE)
		, new LeaveAltar(this.clientContext, ItemConstants.ESSENCE_PURE, ObjectContants.PORTAL_NATURE)
		, new CheckForRun(this.clientContext));
	}
	
	@SuppressWarnings("unchecked")
	public List<Task<ClientContext>> bodyRunesSettings(){
		return Arrays.asList(new WalkToBank(this.clientContext,essence,PathConstants.BODYRUNEPATH , ObjectContants.BANK_BOOTH_BODY)
		, new Banking(this.clientContext, essence, ObjectContants.BANK_BOOTH_BODY)
		, new WalkToAltar(this.clientContext, essence,PathConstants.BODYRUNEPATH, ObjectContants.ALTER_BODY)
		, new EnterAltar(this.clientContext, essence, ObjectContants.ALTER_BODY_ENTRANCE)
		, new CraftRunes(this.clientContext, essence, ObjectContants.ALTER_BODY)
		, new LeaveAltar(this.clientContext, essence, ObjectContants.PORTAL_BODY)
		, new CheckForRun(this.clientContext));
		}


	public int getEssence() {
		return essence;
	}


	public void setEssence(int essence) {
		this.essence = essence;
	}

}
