package tasks;
import methods.MethodFacade;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C> {

	protected MethodFacade methods;
	protected String state;
	
    public String getState() {
		return state;
	}

	public Task(C clientContext) {
        super(clientContext);
        methods = new MethodFacade(clientContext);
    }

    public abstract boolean activate();

    public abstract void execute();

}