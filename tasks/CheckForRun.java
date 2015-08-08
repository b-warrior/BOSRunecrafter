package tasks;

import org.powerbot.script.rt4.ClientContext;

public class CheckForRun extends Task<ClientContext>{

	public CheckForRun(ClientContext clientContext) {
		super(clientContext);
		state = "Put run on";
	}

	@Override
	public boolean activate() {
		return ctx.movement.energyLevel() > 50 && !ctx.movement.running(); 
	}

	@Override
	public void execute() {
		ctx.movement.running(true);
	}

}
