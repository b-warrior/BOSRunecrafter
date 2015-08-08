package tasks;

import org.powerbot.script.rt4.ClientContext;

public class CheckForRun extends Task<ClientContext>{

	int nextCheck;
	public CheckForRun(ClientContext clientContext) {
		super(clientContext);
		state = "Puttin run on";
		nextCheck = getRandomEnergyLevel();
	}

	@Override
	public boolean activate() {
		return ctx.movement.energyLevel() > nextCheck && !ctx.movement.running(); 
	}

	@Override
	public void execute() {
		ctx.widgets.widget(160).component(27).click();
		nextCheck = getRandomEnergyLevel();
	}
	
	private int getRandomEnergyLevel(){
		return (int) (40 + (Math.random() * (100 - 40)));
	}

}
